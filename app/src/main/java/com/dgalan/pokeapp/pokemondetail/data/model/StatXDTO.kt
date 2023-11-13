package com.dgalan.pokeapp.pokemondetail.data.model

import com.google.gson.annotations.SerializedName

data class StatXDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)