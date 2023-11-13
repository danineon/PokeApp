package com.dgalan.pokeapp.pokemondetail.data.model

import com.google.gson.annotations.SerializedName

data class TypeDTO(
    @SerializedName("slot")
    val slot: Int,
    @SerializedName("type")
    val type: TypeXDTO
)