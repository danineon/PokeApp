package com.dgalan.pokeapp.pokemondetail.data.model

import com.google.gson.annotations.SerializedName

data class StatDTO(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("effort")
    val effort: Int,
    @SerializedName("stat")
    val stat: StatXDTO
)