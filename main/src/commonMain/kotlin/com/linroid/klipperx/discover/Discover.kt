package com.linroid.klipperx.discover

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.linroid.klipperx.foundation.koin
import com.linroid.klipperx.moonraker.MoonrakerDiscover
import com.linroid.klipperx.moonraker.MoonrakerInstance
import com.linroid.klipperx.storage.db.Database
import com.linroid.klipperx.ui.Dialog
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal val darkModeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkModeState }

@Composable
internal fun DiscoverScreen(modifier: Modifier = Modifier) {
    val instances = remember { mutableStateListOf<MoonrakerInstance>() }
    var hasFinishedDiscovering by remember { mutableStateOf(false) }

    val toSaveInstance = remember { mutableStateOf<MoonrakerInstance?>(null) }

    val coroutineScope = rememberCoroutineScope()
    DisposableEffect(Unit) {
        val discover = MoonrakerDiscover()
        if (discover.isAvailable()) {
            coroutineScope.launch {
                discover.search().onEach {
                    val db: Database = koin().get()
                    val sort = (db.moonrakerServerQueries.getMaxSort().executeAsOne().MAX ?: 0) + 1
                    db.moonrakerServerQueries.add(it.host, it.port.toLong(), it.name, sort)
                    db.moonrakerServerQueries.getAll().executeAsList().forEach {
                        Napier.d("server: $it")
                    }
                }.collect(instances::add)
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
                Modifier.align(Alignment.Center).animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    "Welcome to KlipperX",
                    style = MaterialTheme.typography.h4,
                )
                Spacer(Modifier.size(32.dp))
                if (instances.isEmpty()) {
                    CircularProgressIndicator(Modifier.size(32.dp))
                    Spacer(Modifier.size(8.dp))
                    Text(
                        "Searching printer ...",
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
                if (instances.size > 0) {
                    Text(
                        "Founded(${instances.size}):",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(Modifier.height(8.dp))
                }
                LazyColumn(
                    Modifier.sizeIn(
                        maxHeight = 300.dp,
                        minWidth = 300.dp,
                        maxWidth = 300.dp
                    )
                ) {
                    items(instances) { instance ->
                        Row(Modifier.fillMaxWidth()) {
                            Card(
                                Modifier
                                    .defaultMinSize(minHeight = 64.dp)
                                    .weight(1f)
                                    .animateItemPlacement()
                                    .clickable { },
                                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.2f),
                                elevation = 0.dp
                            ) {
                                if (instance.name == instance.host) {
                                    Box(Modifier.padding(8.dp).align(Alignment.CenterVertically)) {
                                        Text(
                                            instance.getDisplayName(),
                                            style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.onSecondary,
                                            modifier = Modifier.align(Alignment.CenterStart)
                                        )
                                    }
                                } else {
                                    Column(Modifier.padding(8.dp)) {
                                        Text(
                                            instance.name, style = MaterialTheme.typography.h6,
                                            color = MaterialTheme.colors.onSecondary,
                                        )
                                        Spacer(Modifier.height(4.dp))
                                        Text(
                                            instance.getDisplayName(),
                                            style = MaterialTheme.typography.body1,
                                            color = MaterialTheme.colors.onSecondary,
                                        )
                                    }

                                }
                            }
                            Spacer(Modifier.width(16.dp))
                            IconButton(
                                onClick = {
                                    toSaveInstance.value = instance
                                },
                                Modifier.align(Alignment.CenterVertically)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colors.primary)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    "Add ${instance.getDisplayName()}",
                                    tint = MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                        Spacer(Modifier.height(8.dp))
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
                        style = MaterialTheme.typography.subtitle2,
                    )
                }
            }
        }
        SaveInstanceDialog(toSaveInstance.value, onDismiss = {
            toSaveInstance.value = null
        })
    }
}

@Composable
fun SaveInstanceDialog(instance: MoonrakerInstance?, onDismiss: () -> Unit) {
    if (instance == null) {
        return
    }
    var instanceName = remember { TextFieldValue() }
    Dialog(title = "Add printer", onCloseRequest = onDismiss) {
        val rowHeight = 56.dp
        Column {
            // Text(
            //     "Add printer",
            //     style = MaterialTheme.typography.h6,
            //     modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
            // )
            Row(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                ) {

                    Box(
                        Modifier.defaultMinSize(minHeight = rowHeight, minWidth = 72.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            "Host:",
                            modifier = Modifier.padding(end = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Box(
                        Modifier.defaultMinSize(minHeight = rowHeight, minWidth = 96.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            "Name:",
                            modifier = Modifier.padding(end = 16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    TextField(
                        TextFieldValue(instance.getDisplayName()),
                        enabled = false,
                        modifier = Modifier.defaultMinSize(minHeight = rowHeight),
                        onValueChange = {
                        },
                    )
                    Spacer(Modifier.height(8.dp))
                    TextField(
                        instanceName,
                        modifier = Modifier.defaultMinSize(minHeight = rowHeight),

                        onValueChange = {
                            instanceName = it
                        },
                    )
                }
            }
        }
    }
}