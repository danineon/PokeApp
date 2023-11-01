package com.dgalan.pokeapp.pokemons.data.datasource

import com.dgalan.pokeapp.pokemons.data.api.PokemonApi
import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract.Local
import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract.Remote
import com.dgalan.pokeapp.pokemons.data.model.PokemonDTO
import retrofit2.Retrofit
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val retrofit: Retrofit
) : Remote, Local {

    override suspend fun getPokemonList(page: Int): PokemonDTO {
        return retrofit.create(PokemonApi::class.java).getPokemonList(page)
    }
}