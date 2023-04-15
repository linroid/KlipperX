package com.linroid.klipperx.moonraker

data class Host(val ip: String, val port: Int) {
    companion object {
        val LOOPBACK = Host("127.0.0.1", 80)
        fun parse(host: String): Host {
            if (!host.contains(":")) {
                return Host(host, 80)
            }
            val parts = host.split(":")
            check(parts.size == 2)
            return Host(parts[0], parts[1].toInt())
        }
    }

    override fun toString(): String {
        if (port == 80) {
            return ip
        }
        return "$ip:$port"
    }
}