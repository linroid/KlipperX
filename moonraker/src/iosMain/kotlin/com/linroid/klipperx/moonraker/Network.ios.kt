package com.linroid.klipperx.moonraker

actual fun getScannableNetworks(): List<ScannableNetwork> {
    return emptyList()
}

actual fun getHostNameByIp(ip: String): String {
    return ip
}