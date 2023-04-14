package com.linroid.klipperx.moonraker

data class MoonrakerInstance(
    val host: String,
    val port: Int,
    val name: String,
) {
    val url: String
        get() {
            return if (port == 80) {
                "http://$host"
            } else {
                "http://$host:$port"
            }
        }
}