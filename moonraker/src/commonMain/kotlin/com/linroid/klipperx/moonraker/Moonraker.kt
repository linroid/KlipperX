package com.linroid.klipperx.moonraker

import com.linroid.klipperx.moonraker.model.MoonrakerResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*

class Moonraker(
    private val host: String,
    private val port: Int,
) {
    private val client = HttpClient(CIO) {
        expectSuccess = true
        install(WebSockets)
        install(ContentNegotiation) {
            register(
                ContentType.Application.Json,
                MoonrakerSerializationConverter(),
            )
        }
        HttpResponseValidator {
        }
    }

    suspend fun takeToken(): String
    {
        val response = client.get("http://$host:$port/access/oneshot_token")
        val token: MoonrakerResponse<String> = response.body()
        return token.result
    }
}