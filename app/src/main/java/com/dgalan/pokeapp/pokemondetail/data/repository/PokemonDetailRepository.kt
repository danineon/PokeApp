package com.dgalan.pokeapp.pokemondetail.data.repository

import com.dgalan.pokeapp.pokemondetail.data.datasource.PokemonDetailDataSourceContract
import com.dgalan.pokeapp.pokemondetail.data.mapper.toPokemonDetail
import com.dgalan.pokeapp.pokemondetail.domain.DomainContract
import com.dgalan.pokeapp.pokemondetail.domain.model.PokemonDetail
import javax.inject.Inject

class PokemonDetailRepository @Inject constructor(
    private val pokemonDetailDataSource: PokemonDetailDataSourceContract.Remote
) : DomainContract.Repository {

    override suspend fun getPokemonDetail(id: Int): PokemonDetail {
        return try {
            pokemonDetailDataSource.getPokemonDetailList(id).toPokemonDetail()
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}

