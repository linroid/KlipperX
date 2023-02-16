package com.linroid.klipperx.moonraker

import kotlinx.serialization.Serializable

@Serializable
data class MoonrakerResponse<T>(val result: T)