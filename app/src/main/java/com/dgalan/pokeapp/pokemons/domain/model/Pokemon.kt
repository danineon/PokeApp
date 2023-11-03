package com.dgalan.pokeapp.pokemons.domain.model

data class Pokemon(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val id: Int,
    val name: String,
    val url: String
)