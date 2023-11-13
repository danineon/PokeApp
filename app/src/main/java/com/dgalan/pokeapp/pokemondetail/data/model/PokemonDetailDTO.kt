package com.dgalan.pokeapp.pokemondetail.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("stats")
    val stats: List<StatDTO>,
    @SerializedName("types")
    val types: List<TypeDTO>
)