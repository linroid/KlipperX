package com.linroid.klipperx.moonraker

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface

private fun InetAddress.toIntAddress(): Int {
    check(this is Inet4Address)
    val bytes = address
    var ip = 0
    for (index in 0..3) {
        ip = ip or ((bytes[index].toInt() and 0xFF) shl 8 * (3 - index))
    }
    return ip
}

actual fun getScannableNetworks(): List<ScannableNetwork> {
    val results = mutableListOf<ScannableNetwork>()
    NetworkInterface.getNetworkInterfaces()
        .asSequence()
        .forEach { ni ->
            val address = ni.interfaceAddresses
                .find { it.address is Inet4Address && it.address.isSiteLocalAddress }
            if (address != null) {
                address.broadcast.address
                results.add(
                    ScannableNetwork(
                        name = ni.displayName,
                        address = address.address.toIntAddress(),
                        broadcast = address.broadcast.toIntAddress(),
                        maskLength = address.networkPrefixLength
                    )
                )
            }
        }
    return results
}

actual fun getHostNameByIp(ip: String): String {
    return InetAddress.getByName(ip)?.hostName ?: ip
}