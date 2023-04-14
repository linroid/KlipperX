package com.linroid.klipperx.discover

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

import com.linroid.klipperx.moonraker.MoonrakerDiscover
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

internal val darkModeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
// internal val SafeArea = compositionLocalOf { safeAreaState }
// internal val DarkMode = compositionLocalOf { darkModeState }

@Composable
internal fun DiscoverScreen(modifier: Modifier = Modifier, onAdd: (String?) -> Unit) {
    val hosts = remember { mutableStateListOf<String>() }
    var hasFinishedDiscovering by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        val discover = MoonrakerDiscover()
        if (discover.isAvailable()) {
            coroutineScope.launch {
                discover.search().collect(hosts::add)
                hasFinishedDiscovering = true
            }
        } else {
            Napier.e("discover is not available")
            hasFinishedDiscovering = true
        }
        onDispose {
            discover.dispose()
        }
    }
    Surface(color = MaterialTheme.colors.background) {
        Box(modifier.fillMaxSize()) {
            Column(
                Modifier.align(Alignment.Center).animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Welcome to KlipperX",
                    style = MaterialTheme.typography.h4,
                )
                Spacer(Modifier.size(32.dp))
                if (hosts.isEmpty() && !hasFinishedDiscovering) {
                    CircularProgressIndicator(Modifier.size(32.dp))
                    Spacer(Modifier.size(8.dp))
                    Text(
                        "Searching printer ...",
                        style = MaterialTheme.typography.subtitle2,
                    )
                } else {
                    IconButton(
                        onClick = {
                            onAdd(null)
                        },
                        Modifier.align(Alignment.CenterHorizontally)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            "Add printer",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }

                if (hosts.size > 0) {
                    Text(
                        "Founded(${hosts.size}):",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(Modifier.height(16.dp))
                }
                LazyColumn(
                    Modifier.sizeIn(
                        maxHeight = 300.dp,
                        minWidth = 300.dp,
                        maxWidth = 300.dp
                    )
                ) {
                    items(hosts) { host ->
                        Row(Modifier.fillMaxWidth().clickable {
                            onAdd(host)
                        }) {
                            Card(
                                Modifier
                                    .defaultMinSize(minHeight = 64.dp)
                                    .weight(1f)
                                    .animateItemPlacement(),
                                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.2f),
                                elevation = 0.dp
                            ) {
                                Box(Modifier.padding(8.dp).align(Alignment.CenterVertically)) {
                                    Row(modifier = Modifier.align(Alignment.CenterStart)) {
                                        Icon(
                                            imageVector = Icons.Outlined.KeyboardArrowRight,
                                            contentDescription = null
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            host,
                                            style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.onSecondary,
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
            if (hosts.size > 0 && !hasFinishedDiscovering) {
                Row(
                    Modifier.align(Alignment.BottomEnd).padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(Modifier.size(32.dp))
                    Spacer(Modifier.size(8.dp))
                    Text(
                        "Searching printer ...",
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        }
    }
}
