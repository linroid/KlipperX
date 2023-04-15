package com.linroid.klipperx.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection

val safeAreaState = mutableStateOf(PaddingValues())
val LocalSafeArea = compositionLocalOf { safeAreaState }

@Composable
internal fun SafeArea(
    statusBarColor: Color = MaterialTheme.colors.primarySurface,
    navigationBarColor: Color = Color.Transparent,
    content: @Composable BoxScope.() -> Unit
) {
    val padding = LocalSafeArea.current.value
    Column(
        Modifier.padding(
            start = padding.calculateStartPadding(LayoutDirection.Ltr),
            end = padding.calculateEndPadding(LayoutDirection.Ltr),
        )
    ) {
        Spacer(
            Modifier.fillMaxWidth()
                .height(padding.calculateTopPadding())
                .background(statusBarColor)
        )
        Box(Modifier.weight(1f)) {
            content()
        }
        Spacer(
            Modifier.fillMaxWidth()
                .height(padding.calculateBottomPadding())
                .background(navigationBarColor)
        )
    }
}