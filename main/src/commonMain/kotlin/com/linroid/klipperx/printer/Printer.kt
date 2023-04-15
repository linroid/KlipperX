package com.linroid.klipperx.printer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BuildCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Support
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Dataset
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.linroid.klipperx.LocalSafeArea
import com.linroid.klipperx.moonraker.Host
import com.linroid.klipperx.foundation.koin
import com.linroid.klipperx.moonraker.MoonrakerSession
import com.linroid.klipperx.moonraker.connectMoonrakerSession
import com.linroid.klipperx.storage.MoonrakerServer
import com.linroid.klipperx.storage.db.Database
import com.linroid.klipperx.theme.green200
import com.linroid.klipperx.theme.red200
import com.linroid.klipperx.theme.red500

@Composable
internal fun PrinterScreen(host: Host) {
    val server = remember { mutableStateOf<MoonrakerServer?>(null) }
    Column(Modifier.padding(LocalSafeArea.current.value)) {
        TopAppBar(
            title = {
                Text(server.value?.alias ?: host.toString())
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Notifications, contentDescription = "notifications")
                }
            }
        )
        val session = remember(host) { mutableStateOf<MoonrakerSession?>(null) }
        LaunchedEffect(host) {
            if (host != Host.LOOPBACK) {
                val db: Database = koin().get()
                server.value = db.moonrakerServerQueries.findByHost(host.toString()).executeAsOne()
            } else {
                server.value = MoonrakerServer(0, host.ip, "My Printer", 0)
            }
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
    val scrollState = rememberScrollState()
    Column(Modifier.weight(1f).fillMaxWidth()) {
        val gridState = rememberLazyGridState()
        LazyVerticalGrid(
            columns = GridCells.Adaptive(300.dp),
            state = gridState,
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Box(Modifier.padding(8.dp)) {
                    MoveActionsCard()
                }
            }
            item {
                Box(Modifier.padding(8.dp)) {
                    SensorsCard()
                }
            }
        }
    }
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Sensors, contentDescription = null) },
            label = { Text("") },
            selected = true,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.BuildCircle, contentDescription = null) },
            label = { Text("") },
            selected = false,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Support, contentDescription = null) },
            label = { Text("") },
            selected = false,
            onClick = {
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
            label = { Text("") },
            selected = false,
            onClick = {
            }
        )
    }
}

@Composable
private fun SensorsCard() {
    Card(elevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
        Box(Modifier.height(200.dp).padding(16.dp)) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Sensors, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "Sensors",
                        style = MaterialTheme.typography.subtitle1.copy(MaterialTheme.colors.onSurface)
                    )
                }
                Spacer(Modifier.height(16.dp))
                LazyRow {
                    item {

                        Column(
                            Modifier.width(150.dp).clip(RoundedCornerShape(16.dp))
                                .background(red200)
                        ) {
                            Column(Modifier.padding(8.dp)) {
                                Text("Extruder")
                                Spacer(Modifier.height(4.dp))
                                Text("270 C", style = MaterialTheme.typography.h6)
                                Spacer(Modifier.height(8.dp))
                                Text("On")
                            }
                            Box(
                                Modifier.fillMaxWidth().height(48.dp)
                                    .background(red500)
                            ) {
                                Text(
                                    "Set",
                                    style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onPrimary),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                        Spacer(Modifier.width(16.dp))
                        Column(
                            Modifier.width(150.dp).clip(RoundedCornerShape(16.dp))
                                .background(green200)
                        ) {
                            Column(Modifier.padding(8.dp)) {
                                Text("Bed")
                                Spacer(Modifier.height(4.dp))
                                Text("50.3 C", style = MaterialTheme.typography.h6)
                                Spacer(Modifier.height(8.dp))
                                Text("On")
                            }
                            Box(
                                Modifier.fillMaxWidth().height(48.dp)
                                    .background(MaterialTheme.colors.primary)
                            ) {
                                Text(
                                    "Set",
                                    style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onPrimary),
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MoveActionsCard() {
    Card(elevation = 8.dp, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.height(180.dp)) {
                Box(Modifier.size(180.dp, 180.dp)) {
                    IconButton(
                        onClick = {}, modifier = Modifier.align(Alignment.CenterStart)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.rotate(90f),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.TopCenter)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.rotate(90f),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.Center)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.CenterEnd)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.rotate(180f),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier.align(Alignment.BottomCenter)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.rotate(270f),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
                Spacer(Modifier.width(48.dp))
                Column {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.rotate(90f),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.rotate(270f),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
            Row {
                Row(
                    Modifier.clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Home,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "All",
                        style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onPrimary)
                    )
                }
                Spacer(Modifier.width(24.dp))
                Row(
                    Modifier.clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Dataset,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "QGL",
                        style = MaterialTheme.typography.button.copy(color = MaterialTheme.colors.onPrimary)
                    )
                }
            }
        }
    }
}
