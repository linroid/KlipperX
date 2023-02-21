package com.linroid.klipperx.moonraker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import androidx.core.content.ContextCompat
import org.koin.java.KoinJavaComponent.get
import java.net.Inet4Address
import java.net.InetAddress


private fun InetAddress.toIntAddress(): Int {
    check(this is Inet4Address) {"$this is not a Inet4Address"}
    val bytes = address
    var ip = 0
    for (index in 0..3) {
        ip = ip or ((bytes[index].toInt() and 0xFF) shl 8 * (3 - index))
    }
    return ip
}

@Suppress("DEPRECATION")
actual fun getScannableNetworks(): List<ScannableNetwork> {
    val context: Context = get(Context::class.java)
    val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val connectionInfo = wm.connectionInfo ?: return emptyList()
    if (connectionInfo.ipAddress == 0) {
        return emptyList()
    }

    val connectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java) ?: return emptyList()
    val network = connectivityManager.activeNetwork
    val linkProperties = connectivityManager.getLinkProperties(network) ?: return emptyList()
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return emptyList()
    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
        return linkProperties.linkAddresses.filter { it.address is Inet4Address }.map { address ->
            val addressValue = address.address.toIntAddress()
            val broadcastValue = addressValue or ((1 shl (32 - address.prefixLength)) - 1)
            ScannableNetwork(
                name = linkProperties.interfaceName ?: "",
                address = addressValue,
                broadcast = broadcastValue,
                maskLength = address.prefixLength.toShort(),
            )
        }
    }
    return emptyList()
}

@Suppress("DEPRECATION")
actual fun getHostNameByIp(ip: String): String {
    val context: Context = get(Context::class.java)
    val connectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java) ?: return ip
    val networks = connectivityManager.allNetworks.filter { network ->
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return@filter false
        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }

    networks.forEach {
        val hostname = it.getByName(ip).hostName
        if (!hostname.isNullOrEmpty()) {
            return hostname
        }
    }
    return ip
}