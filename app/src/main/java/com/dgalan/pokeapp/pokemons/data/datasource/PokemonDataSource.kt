package com.dgalan.pokeapp.pokemons.data.datasource

import com.dgalan.pokeapp.pokemons.data.api.PokemonApi
import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract.Local
import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract.Remote
import com.dgalan.pokeapp.pokemons.data.model.PokemonDTO
import com.dgalan.pokeapp.utils.LIMIT_PAGINATION_INT
import com.dgalan.pokeapp.utils.toCapitalize
import retrofit2.Retrofit
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val retrofit: Retrofit
) : Remote, Local {

    override suspend fun getPokemonList(page: Int): PokemonDTO {
        val response = retrofit.create(PokemonApi::class.java).getPokemonList(page, LIMIT_PAGINATION_INT)
        val capitalizedResponse = response.copy(
            results = response.results.map { pokemonResultDTO ->
                pokemonResultDTO.copy(
                    name = pokemonResultDTO.name.toCapitalize()
                )
            }
        )
        return capitalizedResponse
    }
}