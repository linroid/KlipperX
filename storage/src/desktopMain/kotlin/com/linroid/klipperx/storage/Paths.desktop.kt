package com.linroid.klipperx.storage

import okio.Path
import okio.Path.Companion.toPath
import java.io.File

actual fun getDataDirectoryPath(): Path {
    val home = System.getProperty("user.home")
    val rootDir = File(home, ".KlipperX")
    if (!rootDir.exists()) {
        rootDir.mkdir()
    }
    return rootDir.absolutePath.toPath()
}