package com.dgalan.pokeapp.utils

fun String.getIdFromUrl(): Int {
    return substringBeforeLast("/").substringAfterLast("/").toInt()
}