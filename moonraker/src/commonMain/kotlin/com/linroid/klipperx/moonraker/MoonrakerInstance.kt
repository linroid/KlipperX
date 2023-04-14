package com.linroid.klipperx.moonraker

data class MoonrakerInstance(
    val host: String,
    val port: Int,
    val name: String,
) {
    val address: String
        get() {
            return if (port == 80) {
                host
            } else {
                "$host:$port"
            }
        }
    val url: String
        get() = "http://$address"
}