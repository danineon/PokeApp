package com.dgalan.pokeapp.pokemondetail.data.api

import com.dgalan.pokeapp.pokemondetail.data.model.PokemonDetailDTO
import retrofit2.http.GET
import retrofit2.http.Query

fun interface PokemonDetailApi {

    @GET("pokemon")
    suspend fun getPokemon(
        @Query("id") pokemonId: Int
    ): PokemonDetailDTO
}