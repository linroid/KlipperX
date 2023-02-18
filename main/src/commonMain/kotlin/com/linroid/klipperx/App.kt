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
import com.linroid.klipperx.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

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
                    discover.search().collect { host ->
                        instances.add(MoonrakerInstance(host, 80, host))
                    }
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
                    Spacer(Modifier.height(8.dp))
                    if (hasFinishedDiscovering) {
                        Text(
                            "Founded printers",
                            style = typography.subtitle2,
                        )
                    } else {
                        Text(
                            "Searching printer ...",
                            style = typography.subtitle2,
                        )
                        Spacer(Modifier.size(16.dp))
                        CircularProgressIndicator()
                    }
                    Spacer(Modifier.size(16.dp))
                    LazyColumn {
                        items(instances) {
                            Card(
                                Modifier
                                    .defaultMinSize(minWidth = 250.dp, minHeight = 64.dp)
                                    .animateItemPlacement()
                                    .clickable { },
                                backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.4f),
                                elevation = 0.dp
                            ) {
                                if (it.name == it.host) {
                                    Box(Modifier.padding(8.dp)) {
                                        Text(
                                            it.getDisplayName(),
                                            style = typography.h6,
                                            modifier = Modifier.align(Alignment.CenterStart)
                                        )
                                    }
                                } else {
                                    Column(Modifier.padding(8.dp)) {
                                        Text(it.name, style = typography.h6)
                                        Spacer(Modifier.height(4.dp))
                                        Text(it.getDisplayName(), style = typography.body1)
                                    }

                                }
                            }
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                    Button({}) {
                        Text("Manually Add")
                    }
                }
            }
        }
    }
}
