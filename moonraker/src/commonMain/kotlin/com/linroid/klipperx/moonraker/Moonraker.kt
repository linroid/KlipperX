package com.linroid.klipperx.moonraker

import ServerInfo
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
import io.ktor.utils.io.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json

class Moonraker(private val session: MoonrakerSession) {

    suspend fun serverInfo() {
        val serverInfo: ServerInfo = session.request("server.info")
        println(serverInfo)
    }
}