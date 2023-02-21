package com.linroid.klipperx

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import com.linroid.klipperx.theme.AppTheme


internal val LocalWindowSizeClass = compositionLocalOf<WindowSizeClass> {
    error("CompositionLocal LocalWindowSizeClass not present")
}

@Composable
internal fun KlipperXWindow(content: @Composable () -> Unit) {
    AppTheme {
        BoxWithConstraints {
            val windowSizeClass = with(LocalDensity.current) {
                WindowSizeClass.calculateFromSize(
                    DpSize(
                        constraints.maxWidth.toDp(),
                        constraints.maxHeight.toDp()
                    )
                )
            }
            CompositionLocalProvider(
                LocalWindowSizeClass provides windowSizeClass
            ) {
                content()
            }
        }
    }
}
