package com.jfinex.collection.ui.config.components.xor

import android.util.Base64

fun xorEn(data: String, key: String = "6G&10.l0"): String {
    val dataBytes = data.toByteArray(Charsets.UTF_8)
    val keyLength = key.length
    val xorBytes = ByteArray(dataBytes.size) { i ->
        (dataBytes[i].toInt() xor key[i % keyLength].code).toByte()
    }
    return Base64.encodeToString(xorBytes, Base64.NO_WRAP)
}

fun xorDe(encoded: String, key: String = "6G&10.l0"): String {
    val decoded = Base64.decode(encoded, Base64.NO_WRAP)
    val keyLength = key.length
    val originalBytes = ByteArray(decoded.size) { i ->
        (decoded[i].toInt() xor key[i % keyLength].code).toByte()
    }
    return originalBytes.toString(Charsets.UTF_8)
}