package com.linroid.klipperx.storage

import android.content.Context
import com.linroid.klipperx.foundation.koin
import okio.Path
import okio.Path.Companion.toPath
import java.io.File

actual fun getDataDirectoryPath(): Path {
    val context: Context = koin().get()
    return context.filesDir.absolutePath.toPath()
}