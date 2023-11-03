package com.dgalan.pokeapp.pokemons.data.mapper

import com.dgalan.pokeapp.pokemons.data.model.PokemonDTO
import com.dgalan.pokeapp.pokemons.data.model.PokemonResultDTO
import com.dgalan.pokeapp.pokemons.domain.model.Pokemon
import com.dgalan.pokeapp.pokemons.domain.model.PokemonResult
import com.dgalan.pokeapp.utils.getIdFromUrl

fun PokemonDTO.toPokemon(): Pokemon {
    return Pokemon(
        count = count,
        next = next,
        previous = previous,
        results = results.map { it.toResult() }
    )
}

fun PokemonResultDTO.toResult(): PokemonResult {
    return PokemonResult(
        id = url.getIdFromUrl(),
        name = name,
        url = url
    )
}