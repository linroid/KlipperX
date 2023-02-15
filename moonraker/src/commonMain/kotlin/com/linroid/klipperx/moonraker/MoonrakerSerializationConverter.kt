package com.linroid.klipperx.moonraker

import com.linroid.klipperx.moonraker.model.MoonrakerResponse
import io.ktor.serialization.*
import io.ktor.util.reflect.*
import io.ktor.utils.io.*
import io.ktor.utils.io.charsets.*
import io.ktor.utils.io.core.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer

class MoonrakerSerializationConverter(private val json: Json = Json) : ContentConverter {
    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    override suspend fun deserialize(charset: Charset, typeInfo: TypeInfo, content: ByteReadChannel): Any? {
        val contentPacket = content.readRemaining()
        val body = contentPacket.readText(charset)
        try {
            val jsonObject = json.decodeFromString<JsonObject>(body)
            if (jsonObject.containsKey("error")) {
                val code = jsonObject["code"]?.jsonPrimitive?.int
                val message = jsonObject["message"]?.jsonPrimitive?.toString()
                if (code == null || message == null) {
                    throw IllegalArgumentException("Invalid response body: $body")
                }
                throw MoonrakerException(code, message)
            }
            val result: JsonElement = if (MoonrakerResponse::class == typeInfo.type) {
                jsonObject
            } else {
                jsonObject["result"] ?: throw JsonConvertException("Illegal input")
            }
            val deserializationStrategy = json.serializersModule.getContextual(typeInfo.type)
            val mapper =
                deserializationStrategy ?: (typeInfo.kotlinType?.let { serializer(it) } ?: typeInfo.type.serializer())
            return Json.decodeFromJsonElement(mapper, result)
        } catch (cause: Throwable) {
            throw JsonConvertException("Illegal input", cause)
        }
    }

}