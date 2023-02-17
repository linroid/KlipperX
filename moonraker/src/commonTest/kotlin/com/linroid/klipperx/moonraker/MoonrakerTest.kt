package com.linroid.klipperx.moonraker

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class MoonrakerTest {
    // TODO: Mock the websocket
    @Test
    fun whenRequestServerInfoShouldNoException() = runTest {
        // val session = connectMoonrakerSession(
        //     host = "voron.local",
        //     port = 80,
        //     MockEngine { request ->
        //         if (request.url.toString().endsWith("access/oneshot_token")) {
        //             return@MockEngine respond(
        //                 content = ByteReadChannel("""{"result":"token"}""".trimIndent()),
        //                 status = HttpStatusCode.OK,
        //                 headers = headersOf(HttpHeaders.ContentType, "application/json")
        //             )
        //         }
        //         respondError(HttpStatusCode.NotFound)
        //     }
        // )
        // val moonraker = Moonraker(session)
        // delay(1000)
        // moonraker.serverInfo()
    }

}