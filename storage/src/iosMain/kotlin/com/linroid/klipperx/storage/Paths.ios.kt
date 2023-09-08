package com.linroid.klipperx.storage

import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@ExperimentalForeignApi
actual fun getDataDirectoryPath(): Path {
    val url = NSFileManager.defaultManager.URLForDirectory(
        directory = NSApplicationSupportDirectory,
        inDomain = NSUserDomainMask,
        error = null,
        appropriateForURL = null,
        create = true
    )
    return url.toString().toPath()
}