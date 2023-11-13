package com.dgalan.pokeapp.pokemondetail.data.datasource

import com.dgalan.pokeapp.pokemondetail.data.api.PokemonDetailApi
import com.dgalan.pokeapp.pokemondetail.data.datasource.PokemonDetailDataSourceContract.Local
import com.dgalan.pokeapp.pokemondetail.data.datasource.PokemonDetailDataSourceContract.Remote
import com.dgalan.pokeapp.pokemondetail.data.model.PokemonDetailDTO
import retrofit2.Retrofit
import javax.inject.Inject

class PokemonDetailDataSource @Inject constructor(
    private val retrofit: Retrofit
) : Remote, Local {

    override suspend fun getPokemonDetailList(id: Int): PokemonDetailDTO {
        return retrofit.create(PokemonDetailApi::class.java).getPokemonDetail(id)
    }
}