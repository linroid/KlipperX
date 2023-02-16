package com.linroid.klipperx.moonraker

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.utils.io.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MoonrakerSerializationConverterTest {
    @Test
    fun whenServerErrorShouldThrowException() = runTest {
        val client = clientOf(MockEngine { _ ->
            respond(
                content = ByteReadChannel(""),
                status = HttpStatusCode.ServiceUnavailable,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        })
        assertFailsWith(ServerResponseException::class) {
            client.sampleRequest<String>()
        }
    }

    private suspend inline fun <reified T> HttpClient.sampleRequest(): T {
        val response = get("http://127.0.0.1/test")
        return response.body()
    }

    @Test
    fun whenApiErrorShouldThrowMoonrakerException() = runTest {
        val errorMessage = "some errors"
        val client = clientOf(MockEngine { _ ->
            respond(
                content = ByteReadChannel("""{"error": { "code":404, "message": "$errorMessage" } }""".trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        })
        assertFailsWith(MoonrakerException::class, errorMessage) {
            client.sampleRequest<Person>()
        }
    }


    @Test
    fun whenIllegalResponseShouldThrowException() = runTest {
        val client = clientOf(MockEngine { _ ->
            respond(
                content = ByteReadChannel("""{invalid data}""".trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        })
        assertFailsWith(JsonConvertException::class) {
            client.sampleRequest<Person>()
        }
    }

    @Test
    fun whenNoResultKeyShouldThrowContentConvertException() = runTest {
        val client = clientOf(MockEngine { _ ->
            respond(
                content = ByteReadChannel("""{ "age": 18, "name": "foo" }""".trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        })
        assertFailsWith<ContentConvertException> {
            client.sampleRequest<Person>()
        }
    }
    @Test
    fun whenSucceedRequestShouldNoException() = runTest {
        val client = clientOf(MockEngine { _ ->
            respond(
                content = ByteReadChannel("""{"result": { "age": 18, "name": "foo" } }""".trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        })
        val person = client.sampleRequest<Person>()
        assertEquals(person.age, 18)
        assertEquals(person.name, "foo")
    }

    private fun clientOf(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            expectSuccess = true
            install(ContentNegotiation) {
                register(
                    ContentType.Application.Json,
                    MoonrakerSerializationConverter(),
                )
            }
            HttpResponseValidator {
            }
        }
    }

    @Serializable
    class Person(val age: Int, val name: String)
}