package com.jfinex.collection.ui.config.exportConfig

internal const val KEY_SEP: Char = '\u001F'

internal fun makeKey(name: String, block: String): String =
    buildString { append(name); append(KEY_SEP); append(block) }

internal fun parseKey(composite: String): Pair<String, String> {
    val idx = composite.indexOf(KEY_SEP)
    return if (idx >= 0) {
        val name = composite.substring(0, idx)
        val block = composite.substring(idx + 1)
        name to block
    } else {
        composite to ""
    }
}