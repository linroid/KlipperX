package com.linroid.klipperx.moonraker

data class MoonrakerInstance(
    val host: String,
    val port: Int,
    val name: String,
) {
    fun getDisplayName():String {
        return if (port == 80) {
            "http://$host"
        } else {
            "http://$host:$port"
        }
    }
}