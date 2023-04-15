package com.linroid.klipperx

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import com.linroid.klipperx.theme.AppTheme
import com.linroid.klipperx.ui.LocalSafeArea
import com.linroid.klipperx.ui.safeAreaState

internal val LocalWindowSizeClass = compositionLocalOf<WindowSizeClass> {
    error("CompositionLocal LocalWindowSizeClass not present")
}

@Composable
internal fun KlipperXWindow(content: @Composable () -> Unit) {
    AppTheme(darkTheme = isSystemInDarkTheme()) {
        BoxWithConstraints(Modifier.background(MaterialTheme.colors.background)) {
            val windowSizeClass = with(LocalDensity.current) {
                WindowSizeClass.calculateFromSize(
                    DpSize(
                        constraints.maxWidth.toDp(),
                        constraints.maxHeight.toDp()
                    )
                )
            }
            CompositionLocalProvider(
                LocalWindowSizeClass provides windowSizeClass,
                LocalSafeArea provides safeAreaState,
            ) {
                content()
            }
        }
    }
}
