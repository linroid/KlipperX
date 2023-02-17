package com.linroid.klipperx.moonraker

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
import kotlin.reflect.KClass
import kotlin.reflect.KType

internal class MoonrakerSerializationConverter(private val json: Json = Json) : ContentConverter {
    override suspend fun deserialize(charset: Charset, typeInfo: TypeInfo, content: ByteReadChannel): Any? {
        val contentPacket = content.readRemaining()
        val text = contentPacket.readText(charset)
        val jsonObject = try {
            json.decodeFromString<JsonObject>(text)
        } catch (error: Exception) {
            throw JsonConvertException("Invalid json body")
        }
        return serializeMoonrakerResponse(json, jsonObject, typeInfo.type, typeInfo.kotlinType)
    }

}

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
internal fun serializeMoonrakerResponse(json: Json, jsonObject: JsonObject, type: KClass<*>, kotlinType: KType?): Any? {
    if (jsonObject.containsKey("error")) {
        val errorObject = jsonObject["error"]?.jsonObject!!
        val code = errorObject["code"]?.jsonPrimitive?.int
        val message = errorObject["message"]?.jsonPrimitive?.toString()
        if (code == null || message == null) {
            throw IllegalArgumentException("Invalid response body")
        }
        throw MoonrakerException(code, message)
    }
    val result: JsonElement = if (MoonrakerResponse::class == type) {
        jsonObject
    } else {
        jsonObject["result"] ?: throw ContentConvertException("Illegal input")
    }
    val deserializationStrategy = json.serializersModule.getContextual(type)

    val mapper =
        deserializationStrategy ?: (kotlinType?.let { serializer(it) } ?: type.serializer())
    return json.decodeFromJsonElement(mapper, result)
}