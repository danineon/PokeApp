package com.dgalan.pokeapp.pokemons.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<PokemonResultDTO>
)

data class PokemonResultDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)