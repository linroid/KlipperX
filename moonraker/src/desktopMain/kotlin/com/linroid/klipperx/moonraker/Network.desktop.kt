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
    val interfaces = NetworkInterface.getNetworkInterfaces() ?: return results
    interfaces
        .asSequence()
        .forEach { ni ->
            val address = ni.interfaceAddresses
                .find { it.address is Inet4Address && it.address.isSiteLocalAddress }
            if (address != null) {
                val addressValue = address.address.toIntAddress()
                val broadcastValue = if (address.broadcast != null) {
                    address.address.toIntAddress()
                } else {
                    addressValue or ((1 shl (32 - address.networkPrefixLength))-1)
                }
                results.add(
                    ScannableNetwork(
                        name = ni.displayName,
                        address = addressValue,
                        broadcast = broadcastValue,
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