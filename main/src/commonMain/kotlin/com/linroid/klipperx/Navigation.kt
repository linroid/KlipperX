package com.linroid.klipperx

import androidx.compose.runtime.Composable
import com.linroid.klipperx.discover.DiscoverScreen
import com.linroid.klipperx.instance.AddInstanceScreen
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun NavigationHost() {
    val navigator = rememberNavigator()
    KlipperXWindow {
        NavHost(
            navigator = navigator,
            initialRoute = "/discover"
        ) {
            scene("/discover") {
                DiscoverScreen(onAdd = {
                    navigator.navigate("/add_instance?host=${it.host}")
                })
            }
            scene("/add_instance") { entry ->
                val host = entry.query<String>("host")
                AddInstanceScreen(host, onAdded = {
                    navigator.navigate("/home")
                })
            }
        }
    }
}