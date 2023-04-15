package com.linroid.klipperx

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.linroid.klipperx.discover.DiscoverScreen
import com.linroid.klipperx.moonraker.Host
import com.linroid.klipperx.instance.AddInstanceScreen
import com.linroid.klipperx.printer.PrinterScreen
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import org.koin.compose.koinInject

@Composable
internal fun NavigationHost(hostMode: Boolean = false, settings: Settings = koinInject()) {
    val navigator = rememberNavigator()
    val defaultHost = settings.get<String>(SettingsKeys.DefaultHost)
    KlipperXWindow {
        NavHost(
            navigator = navigator,
            initialRoute = if (defaultHost == null && !hostMode) "/discover" else "/printer"
        ) {
            scene("/discover") {
                DiscoverScreen(onAdd = { host ->
                    if (host == null) {
                        navigator.navigate("/add_instance")
                    } else {
                        navigator.navigate("/add_instance?host=$host")
                    }
                })
            }
            scene("/add_instance") { entry ->
                val host = entry.query<String>("host")
                AddInstanceScreen(host, onAdded = {
                    navigator.navigate("/printer")
                })
            }
            scene("/printer") { entry ->
                var hostString = entry.query<String>("host")
                if (hostString == null) {
                    hostString = settings[SettingsKeys.DefaultHost]
                }
                val host = if (hostString == null) Host.LOOPBACK else Host.parse(hostString)
                PrinterScreen(host)
            }
        }
    }
}