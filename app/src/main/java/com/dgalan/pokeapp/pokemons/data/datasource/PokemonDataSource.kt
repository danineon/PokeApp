package com.dgalan.pokeapp.pokemons.data.datasource

import com.dgalan.pokeapp.pokemons.data.api.PokemonApi
import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract.Local
import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract.Remote
import com.dgalan.pokeapp.pokemons.data.model.PokemonDTO
import retrofit2.Retrofit
import java.util.Locale
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val retrofit: Retrofit
) : Remote, Local {

    override suspend fun getPokemonList(page: Int): PokemonDTO {
        val response = retrofit.create(PokemonApi::class.java).getPokemonList(page)
        val capitalizedResponse = response.copy(
            results = response.results.map { pokemonResultDTO ->
                pokemonResultDTO.copy(
                    name = capitalizePokemonName(pokemonResultDTO.name)
                )
            }
        )
        return capitalizedResponse
    }
}

private fun capitalizePokemonName(name: String): String {
    return name.replaceFirstChar { firstChar ->
        if (firstChar.isLowerCase()) {
            firstChar.titlecase(Locale.getDefault())
        } else {
            firstChar.toString()
        }
    }
}