package com.linroid.klipperx.ui

import androidx.compose.runtime.Composable

@Composable
expect fun Dialog(
    title: String,
    onCloseRequest: () -> Unit,
    content: @Composable () -> Unit
)