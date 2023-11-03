package com.dgalan.pokeapp.pokemons.domain

import com.dgalan.pokeapp.pokemons.domain.model.Pokemon

interface DomainContract {
    fun interface Repository {

        suspend fun getPokemonList(page: Int): Pokemon
    }

    interface UseCase {

    }
}