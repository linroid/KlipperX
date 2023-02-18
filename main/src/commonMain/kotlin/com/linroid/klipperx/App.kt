package com.linroid.klipperx

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.linroid.klipperx.theme.AppTheme

internal val darkModeState = mutableStateOf(false)
internal val safeAreaState = mutableStateOf(PaddingValues())
internal val SafeArea = compositionLocalOf { safeAreaState }
internal val DarkMode = compositionLocalOf { darkModeState }

@Composable
internal fun App(modifier: Modifier = Modifier) {
    AppTheme {
        Surface {
            Box(modifier.fillMaxSize()) {
                Column(Modifier.align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Welcome to KlipperX",
                        style = typography.h4,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Searching printer ...",
                        style = typography.subtitle2,
                    )
                    Spacer(Modifier.size(16.dp))
                    CircularProgressIndicator()
                }
            }
        }
    }
}
