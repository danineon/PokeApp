package com.dgalan.pokeapp.utils

import java.util.Locale

fun String.getIdFromUrl(): Int {
    return substringBeforeLast("/").substringAfterLast("/").toInt()
}

fun String.toLinearProgressBar(): Float {
    return this.toFloat() / 255f
}

fun String.toCapitalize(): String {
    return replaceFirstChar { firstChar ->
        if (firstChar.isLowerCase()) {
            firstChar.titlecase(Locale.getDefault())
        } else {
            firstChar.toString()
        }
    }
}