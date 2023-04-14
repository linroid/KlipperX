package com.linroid.klipperx.instance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.linroid.klipperx.SettingsKeys
import com.linroid.klipperx.foundation.koin
import com.linroid.klipperx.storage.db.Database
import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import io.github.aakira.napier.Napier

@Composable
internal fun AddInstanceScreen(host: String?, onAdded: () -> Unit) {
    var hostValue by remember { mutableStateOf(TextFieldValue(host ?: "")) }
    var nameValue by remember { mutableStateOf(TextFieldValue("")) }

    val rowHeight = 48.dp
    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add printer",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(top = 96.dp)
        )
        Spacer(Modifier.size(32.dp))
        Row {
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
                modifier = Modifier.width(240.dp)
            ) {
                TextField(
                    hostValue,
                    enabled = host == null,
                    modifier = Modifier.height(rowHeight),
                    onValueChange = {
                        hostValue = it
                    },
                )
                Spacer(Modifier.height(8.dp))
                TextField(
                    nameValue,
                    modifier = Modifier.height(rowHeight),
                    label = {
                        Text(host ?: "")
                    },
                    onValueChange = {
                        nameValue = it
                    },
                )
                Spacer(Modifier.height(8.dp))
                Button(
                    modifier = Modifier.defaultMinSize(minWidth = 200.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = MaterialTheme.shapes.medium,
                    onClick = {
                        val db: Database = koin().get()
                        val sort =
                            (db.moonrakerServerQueries.getMaxSort().executeAsOne().MAX ?: 0) + 1
                        // TODO: handle in background thread
                        val name = nameValue.text.ifEmpty { null }
                        // TODO: Validate host address
                        db.moonrakerServerQueries.add(hostValue.text, name, sort)
                        val settings: Settings = koin().get()
                        settings[SettingsKeys.DefaultHost] = hostValue.text
                        Napier.d("Save instance: ${hostValue.text} with name: $name")
                        onAdded()
                    },
                ) { Text("Add") }
            }
        }

    }
}
