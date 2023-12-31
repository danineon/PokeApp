package com.dgalan.pokeapp.pokemons.data.datasource

import com.dgalan.pokeapp.pokemons.data.model.PokemonDTO

interface PokemonDataSourceContract {
    fun interface Remote {

        suspend fun getPokemonList(page: Int): PokemonDTO
    }

    interface Local {
    }
}