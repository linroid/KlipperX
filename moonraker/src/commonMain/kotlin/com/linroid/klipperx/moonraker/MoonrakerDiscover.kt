package com.linroid.klipperx.moonraker

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.*
import kotlin.math.pow
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime
import kotlin.time.measureTimedValue

class MoonrakerDiscover(
    private val timeout: Long = 500,
    workerCount: Int = 10
) {

    private val networks = getScannableNetworks()

    private val dispatchers = newFixedThreadPoolContext(workerCount, "mooonraker-discover")
    private val coroutineScope = CoroutineScope(dispatchers)

    private val hosts = flow {
        networks.forEach { network ->
            val mask = -1 shl (32 - network.maskLength.toInt())
            val base = network.address and mask
            val max = 2.0.pow(32 - network.maskLength.toInt()).toInt()
            for (i in 1 until max) {
                if (base + i != network.broadcast) {
                    emit((base + i).toHostAddress())
                }
            }
        }
    }

    fun isAvailable(): Boolean {
        return networks.isNotEmpty()
    }

    fun search(): Flow<String> = channelFlow {
        hosts.map { pingHost(it, this) }
            .toList()
            .awaitAll()
        close()
    }

    private fun pingHost(host: String, scope: ProducerScope<String>): Deferred<String?> {
        return coroutineScope.async {
            try {
                val succeed = measureTimedValue {
                    pingMoonraker(host, timeout)
                }
                if (succeed.value) {
                    scope.send(host)
                    return@async host
                }
                return@async null
            } catch (error: Exception) {
                return@async null
            }
        }
    }

    fun dispose() {
        coroutineScope.cancel()
        dispatchers.close()
    }
}