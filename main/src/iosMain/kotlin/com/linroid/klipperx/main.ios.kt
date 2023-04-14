package com.linroid.klipperx

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.linroid.klipperx.discover.darkModeState
import com.linroid.klipperx.discover.safeAreaState
import moe.tlaster.precompose.PreComposeApplication
import org.jetbrains.skiko.SystemTheme
import org.jetbrains.skiko.currentSystemTheme
import platform.CoreGraphics.CGFloat

@Suppress("unused")
fun mainViewController() = PreComposeApplication("KotlinX") {
    NavigationHost()
}

fun setSafeArea(start: CGFloat, top: CGFloat, end: CGFloat, bottom: CGFloat) {
    safeAreaState.value = PaddingValues(start.dp, top.dp, end.dp, bottom.dp)
}

fun setDarkMode() {
    darkModeState.value = currentSystemTheme == SystemTheme.DARK
}
