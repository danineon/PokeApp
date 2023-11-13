package com.dgalan.pokeapp.pokemons.ui.state

sealed class PokemonUIEvent {
    data object IsShimmerShown : PokemonUIEvent()
}