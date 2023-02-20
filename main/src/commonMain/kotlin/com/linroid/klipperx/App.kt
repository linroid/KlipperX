package com.linroid.klipperx

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.linroid.klipperx.moonraker.MoonrakerDiscover
import com.linroid.klipperx.moonraker.MoonrakerInstance
import com.linroid.klipperx.moonraker.getHostNameByIp
import com.linroid.klipperx.theme.AppTheme
import kotlinx.coroutines.launch

internal val darkModeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkModeState }

@Composable
internal fun App(modifier: Modifier = Modifier) {
    AppTheme {
        val instances = remember { mutableStateListOf<MoonrakerInstance>() }
        var hasFinishedDiscovering by remember { mutableStateOf(false) }

        val coroutineScope = rememberCoroutineScope()
        DisposableEffect(Unit) {
            val discover = MoonrakerDiscover()
            if (discover.isAvailable()) {
                coroutineScope.launch {
                    discover.search().collect(instances::add)
                    hasFinishedDiscovering = true
                }
            }
            onDispose {
                discover.dispose()
            }
        }
        Surface(color = MaterialTheme.colors.background) {
            Box(modifier.fillMaxSize()) {
                Column(
                    Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        "Welcome to KlipperX",
                        style = typography.h4,
                    )
                    Spacer(Modifier.size(32.dp))
                    if (instances.isEmpty()) {
                        CircularProgressIndicator(Modifier.size(32.dp))
                        Spacer(Modifier.size(8.dp))
                        Text(
                            "Searching printer ...",
                            style = typography.subtitle2,
                        )
                    }
                    LazyColumn(Modifier.sizeIn(maxHeight = 300.dp)) {
                        items(instances) {
                            Card(
                                Modifier
                                    .defaultMinSize(minWidth = 250.dp, minHeight = 64.dp)
                                    .animateItemPlacement()
                                    .clickable { },
                                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f),
                                elevation = 0.dp
                            ) {
                                if (it.name == it.host) {
                                    Box(Modifier.padding(8.dp)) {
                                        Text(
                                            it.getDisplayName(),
                                            style = typography.h6,
                                            color = MaterialTheme.colors.onSecondary,
                                            modifier = Modifier.align(Alignment.CenterStart)
                                        )
                                    }
                                } else {
                                    Column(Modifier.padding(8.dp)) {
                                        Text(
                                            it.name, style = typography.h6,
                                            color = MaterialTheme.colors.onSecondary,
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            it.getDisplayName(), style = typography.body1,
                                            color = MaterialTheme.colors.onSecondary,
                                        )
                                    }

                                }
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    Spacer(Modifier.height(8.dp))

                    if (instances.size > 0) {
                        Button(
                            modifier = Modifier.sizeIn(minWidth = 120.dp),
                            onClick = {}
                        ) {
                            Text("Add all")
                        }
                    }
                }
                if (instances.size > 0 && !hasFinishedDiscovering) {
                    Row(
                        Modifier.align(Alignment.BottomEnd).padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(Modifier.size(32.dp))
                        Spacer(Modifier.size(8.dp))
                        Text(
                            "Searching printer ...",
                            style = typography.subtitle2,
                        )
                    }
                }
            }
        }
    }
}
