package com.linroid.klipperx.moonraker

data class ScannableNetwork(
    val name: String,
    val address: Int,
    val broadcast: Int,
    val maskLength: Short
)

expect fun getScannableNetworks(): List<ScannableNetwork>

internal fun Int.toHostAddress(): String {
    val builder = StringBuilder()
    for (i in 0..3) {
        if (i != 0) {
            builder.append('.')
        }
        builder.append((this shr (8 * (3 - i))) and 0xFF)
    }
    return builder.toString()
}

expect fun getHostNameByIp(ip: String): String