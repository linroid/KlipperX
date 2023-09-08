package com.linroid.klipperx

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import com.linroid.klipperx.ui.safeAreaState
import moe.tlaster.precompose.PreComposeApplication
import platform.CoreGraphics.CGFloat

@Suppress("unused")
fun mainViewController() = PreComposeApplication {
    NavigationHost()
}

fun setSafeArea(start: CGFloat, top: CGFloat, end: CGFloat, bottom: CGFloat) {
    safeAreaState.value = PaddingValues(start.dp, top.dp, end.dp, bottom.dp)
}
