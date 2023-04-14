package com.linroid.klipperx.printer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.linroid.klipperx.moonraker.Host
import com.linroid.klipperx.foundation.koin
import com.linroid.klipperx.moonraker.MoonrakerSession
import com.linroid.klipperx.moonraker.connectMoonrakerSession
import com.linroid.klipperx.storage.MoonrakerServer
import com.linroid.klipperx.storage.db.Database

@Composable
internal fun PrinterScreen(host: Host) {
    val server = remember { mutableStateOf<MoonrakerServer?>(null) }
    Column {
        TopAppBar(
            title = {
                Text(server.value?.alias ?: host.toString())
            },
        )
        val session = remember(host) { mutableStateOf<MoonrakerSession?>(null) }
        LaunchedEffect(host) {
            val db: Database = koin().get()
            server.value = db.moonrakerServerQueries.findByHost(host.toString()).executeAsOne()

            session.value = connectMoonrakerSession(host.ip, host.port)
        }
        if (session.value == null) {
            Box(Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        } else {
            ConnectedPrinterSession(session.value!!)
        }
    }
}

@Composable
private fun ColumnScope.ConnectedPrinterSession(session: MoonrakerSession) {
    Box(Modifier.weight(1f)) {
    }
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
            label = { Text("") },
            selected = true,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Add, contentDescription = null) },
            label = { Text("") },
            selected = false,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.AccountBox, contentDescription = null) },
            label = { Text("") },
            selected = false,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = null) },
            label = { Text("") },
            selected = false,
            onClick = {
            }
        )
    }
}