package com.linroid.klipperx.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.native.HiddenFromObjC
import kotlin.native.HidesFromObjC

internal val darkModeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkModeState }

@Composable
internal fun App(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()

    Box(Modifier.fillMaxSize().background(color = Color.Black)) {
        Button(onClick = {
            text = "Hello, $platformName"
        }) {
            Text(text)
        }
    }
}
