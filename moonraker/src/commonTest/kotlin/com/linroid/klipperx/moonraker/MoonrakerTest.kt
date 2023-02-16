package com.linroid.klipperx.moonraker

import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MoonrakerTest {
    @Test
    fun testTakeToken() = runTest {
        val moonraker = moonrakerOf(MockEngine { _ ->
            respond(
                content = ByteReadChannel("""{"result":"token"}""".trimIndent()),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        })
        assertEquals(moonraker.takeToken(), "token")
    }

    private fun moonrakerOf(engine: HttpClientEngine): Moonraker {
        return Moonraker(
            host = "voron.local",
            port = 80,
            engine = engine
        )
    }

}