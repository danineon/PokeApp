package com.dgalan.pokeapp.pokemondetail.ui.state

sealed class PokemonDetailUIEvent {
    data class InitPokemonDetail(val pokemonId: Int) : PokemonDetailUIEvent()
}