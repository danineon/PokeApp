package com.dgalan.pokeapp.pokemondetail.data.datasource

import com.dgalan.pokeapp.pokemondetail.data.model.PokemonDetailDTO

interface PokemonDetailDataSourceContract {
    fun interface Remote {

        suspend fun getPokemonDetailList(id: Int): PokemonDetailDTO
    }

    interface Local {
    }
}