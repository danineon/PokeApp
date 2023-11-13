package com.dgalan.pokeapp.pokemondetail.ui.state

data class PokemonDetailUIState(
    val id: Int = 0,
    val name: String = "",
    val baseStats: List<String> = emptyList(),
    val highlights: List<Boolean> = emptyList(),
    val types: List<String> = emptyList(),
)