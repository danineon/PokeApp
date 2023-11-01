package com.dgalan.pokeapp.pokemons.data.repository

import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract
import com.dgalan.pokeapp.pokemons.data.mapper.toPokemon
import com.dgalan.pokeapp.pokemons.domain.DomainContract
import com.dgalan.pokeapp.pokemons.domain.model.Pokemon
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonDataSource: PokemonDataSourceContract.Remote
) : DomainContract.Repository {

    override suspend fun getPokemonList(page: Int): Pokemon {
        return try {
            pokemonDataSource.getPokemonList(page).toPokemon()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}

