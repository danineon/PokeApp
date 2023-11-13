package com.dgalan.pokeapp.pokemondetail.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val stats: List<Stat>,
    val types: List<Type>
)

data class Stat(
    val baseStat: Int,
    val stat: StatX
)

data class StatX(
    val name: String
)

data class Type(
    val type: TypeX
)

data class TypeX(
    val name: String
)