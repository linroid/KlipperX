package com.linroid.klipperx.moonraker

actual fun getScannableNetworks(): List<ScannableNetwork> {
    // val context: Context by inject(Context::class.java)
    // val connectivityManager =
    //     ContextCompat.getSystemService(context, ConnectivityManager::class.java) ?: return emptyList()
    // val activeNetwork = connectivityManager.activeNetwork ?: return emptyList()
    // val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return emptyList()
    // if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
    // }
    return emptyList()
}

actual fun getHostNameByIp(ip: String): String {
    return ip
}