package com.linroid.klipperx.moonraker.model

import kotlinx.serialization.Serializable

@Serializable
data class MoonrakerResponse<T>(val result: T)