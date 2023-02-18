package com.linroid.klipperx.moonraker

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat


@SuppressLint("StaticFieldLeak")
private var context: Context? = null
fun setupMoonraker(ctx: Context) {
    context = ctx
}

fun discover(context: Context) {
    // val dhcpInfo = (context.getSystemService(Context.WIFI_SERVICE) as WifiManager).dhcpInfo
    // try {
    //     val inetAddress: InetAddress = InetAddress.getByAddress(extractBytes(dhcpInfo.ipAddress))
    //     val networkInterface: NetworkInterface = NetworkInterface.getByInetAddress(inetAddress)
    //     for (address in networkInterface.interfaceAddresses) {
    //         // short netPrefix = address.getNetworkPrefixLength();
    //         Log.d(TAG, address.toString())
    //     }
    // } catch (e: IOException) {
    // }
}

actual fun getScannableNetworks(): List<ScannableNetwork> {
    val context = context
    checkNotNull(context) {
        "Have you called setupMoonraker()?"
    }
    val connectivityManager =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java) ?: return emptyList()
    val activeNetwork = connectivityManager.activeNetwork ?: return emptyList()
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return emptyList()
    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
    }
    return emptyList()
}