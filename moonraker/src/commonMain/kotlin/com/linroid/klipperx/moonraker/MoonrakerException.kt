package com.linroid.klipperx.moonraker

class MoonrakerException(
    private val code: Int,
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause)