package com.linroid.klipperx.moonraker

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import org.koin.java.KoinJavaComponent.inject
import java.net.Inet4Address
import java.net.InetAddress


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
    val context :Context by inject(Context::class.java)
    val wm = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    Log.i("Lin", wm.connectionInfo.ipAddress.toString())
    return emptyList()
}

actual fun getHostNameByIp(ip: String): String {
    val context: Context by inject(Context::class.java)
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