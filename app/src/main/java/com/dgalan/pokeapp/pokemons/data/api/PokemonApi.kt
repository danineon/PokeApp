package com.dgalan.pokeapp.pokemons.data.api

import com.dgalan.pokeapp.pokemons.data.model.PokemonDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") page: Int,
        @Query("limit") limit: Int = 20
    ): PokemonDTO
}