package com.dgalan.pokeapp.pokemondetail.data.api

import com.dgalan.pokeapp.pokemondetail.data.model.PokemonDetailDTO
import retrofit2.http.GET
import retrofit2.http.Path

fun interface PokemonDetailApi {

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") pokemonId: Int
    ): PokemonDetailDTO
}