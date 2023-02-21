package com.linroid.klipperx

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.linroid.klipperx.theme.AppTheme

enum class WindowSizeClass {
    Compact, Medium, Expanded;

    companion object {
        fun width(size: Dp): WindowSizeClass {
            if (size < 600.dp) {
                return Compact
            } else if (size < 1000.dp) {
                return Medium
            }
            return Expanded
        }

        fun height(size: Dp): WindowSizeClass {
            if (size < 600.dp) {
                return Compact
            } else if (size < 1000.dp) {
                return Medium
            }
            return Expanded
        }
    }
}

data class WindowSizeClasses(var width: WindowSizeClass, val height: WindowSizeClass)

internal val LocalWindowSizeClasses = compositionLocalOf {
    WindowSizeClasses(WindowSizeClass.Compact, WindowSizeClass.Compact)
}

@Composable
internal fun KlipperXWindow(content: @Composable () -> Unit) {
    AppTheme {
        BoxWithConstraints {
            val classes = with(LocalDensity.current) {
                WindowSizeClasses(
                    WindowSizeClass.width(constraints.maxWidth.toDp()),
                    WindowSizeClass.height(constraints.maxHeight.toDp())
                )
            }
            CompositionLocalProvider(
                LocalWindowSizeClasses provides classes
            ) {
                content()
            }
        }
    }
}
