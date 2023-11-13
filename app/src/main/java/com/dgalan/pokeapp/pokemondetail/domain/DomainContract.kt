package com.dgalan.pokeapp.pokemondetail.domain

import com.dgalan.pokeapp.pokemondetail.domain.model.PokemonDetail
import com.dgalan.pokeapp.pokemondetail.domain.model.Stat

interface DomainContract {
    fun interface Repository {

        suspend fun getPokemonDetail(id: Int): PokemonDetail
    }

    fun interface UseCase {

        suspend fun getHighlightsStats(stats: List<Stat>): List<Boolean>
    }
}