package com.linroid.klipperx.storage

import android.content.Context
import com.linroid.klipperx.foundation.koin
import okio.Path
import okio.Path.Companion.toPath

actual fun getDataDirectoryPath(): Path {
    val context: Context = koin().get()
    return context.filesDir.absolutePath.toPath()
}