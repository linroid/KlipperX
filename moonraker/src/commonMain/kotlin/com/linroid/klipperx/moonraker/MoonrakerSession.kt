package com.linroid.klipperx.moonraker

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

suspend fun connectMoonrakerSession(
    host: String,
    port: Int,
    engine: HttpClientEngine = CIO.create(),
): MoonrakerSession {
    val client = HttpClient(engine) {
        expectSuccess = true
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json)
        }
        install(Logging) {
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            register(
                ContentType.Application.Json,
                MoonrakerSerializationConverter(),
            )
        }
        HttpResponseValidator {
        }
    }
    val token = try {
        val response = client.get("http://$host:$port/access/oneshot_token")
        val body: MoonrakerResponse<String> = response.body()
        body.result
    } catch (error: Exception) {
        client.close()
        throw error
    }
    val session = try {
        check(token.isNotEmpty())
        client.webSocketSession(
            method = HttpMethod.Get,
            host = host,
            port = port,
            path = "/websocket"
        )
    } catch (error: Exception) {
        client.close()
        throw error
    }
    return MoonrakerSession(client, session, token)
}

class MoonrakerSession(
    private val client: HttpClient,
    private val websocket: WebSocketSession,
    private val token: String,
    private val json: Json = Json
) {

    private var id: Int = 0
    private val mutex = Mutex()
    private val requests = mutableMapOf<Int, Request>()
    private val queue = Channel<Request> { }
    private val receiveDispatcher = newSingleThreadContext("moonraker-receiver")
    private val sendDispatcher = newSingleThreadContext("moonraker-send")
    private val receiveScope = CoroutineScope(receiveDispatcher)
    private val sendScope = CoroutineScope(sendDispatcher)

    init {
        startReceive()
        startSend()
    }

    fun stop() {
        receiveScope.cancel()
        receiveDispatcher.close()
        sendScope.cancel()
        sendDispatcher.close()
        queue.close()
        requests.clear()
        client.close()
    }

    private fun startSend() {
        sendScope.launch {
            queue.consumeAsFlow().collect { request ->
                val jsonObject = buildJsonObject {
                    put("id", request.id)
                    put("jsonrpc", "2.0")
                    put("method", request.method)
                    if (request.params != null) {
                        put("params", json.encodeToJsonElement(request.params))
                    }
                }
                websocket.send(Frame.Text(jsonObject.toString()))
            }
        }
    }

    private fun startReceive() {
        receiveScope.launch {
            websocket.incoming.receiveAsFlow()
                .map { it as Frame.Text }
                .collect {
                    val jsonObject = json.decodeFromString<JsonObject>(it.readText())
                    val id = jsonObject["id"]?.jsonPrimitive?.int
                    if (id == null) {
                        handleNotify(jsonObject)
                        return@collect
                    }
                    val request = mutex.withLock {
                        requests.remove(id)
                    }
                    if (request == null || request.isCanceled) {
                        println("Canceled response(id=$id)")
                        return@collect
                    }
                    val response =
                        try {
                            serializeMoonrakerResponse(json, jsonObject, request.type, request.kotlinType)
                        } catch (error: Exception) {
                            request.continuation.resumeWithException(error)
                            return@collect
                        }
                    request.continuation.resume(response)
                }
        }
    }

    private fun handleNotify(jsonObject: JsonObject) {
        val method = jsonObject["method"]?.toString()
        checkNotNull(method)
        println("$method: ${jsonObject["params"]}")
    }

    suspend inline fun <reified T> request(method: String, params: Map<String, Any?>? = null): T {
        val kotlinType = typeOf<T>()
        return request(method, params, T::class, kotlinType)
    }

    suspend fun <T> request(method: String, params: Map<String, Any?>?, clazz: KClass<*>, kotlinType: KType): T {
        val id = obtainId()

        val response = suspendCancellableCoroutine { cont ->
            val request = Request(
                id = id,
                continuation = cont,
                method = method,
                params = params,
                type = clazz,
                kotlinType = kotlinType
            )
            sendScope.launch {
                enqueue(request)
            }
            cont.invokeOnCancellation {
                request.isCanceled = true
                sendScope.launch {
                    mutex.withLock {
                        requests.remove(request.id)
                    }
                }
            }
        }
        @Suppress("UNCHECKED_CAST")
        return response as T
    }

    private suspend fun enqueue(request: Request) {
        mutex.withLock {
            requests[request.id] = request
        }
        queue.send(request)
    }

    private suspend fun obtainId(): Int {
        return mutex.withLock { ++id }
    }

    private data class Request(
        val id: Int,
        val continuation: Continuation<Any?>,
        val method: String,
        val params: Map<String, Any?>?,
        val type: KClass<*>,
        val kotlinType: KType,
    ) {
        var isCanceled: Boolean = false
    }

}