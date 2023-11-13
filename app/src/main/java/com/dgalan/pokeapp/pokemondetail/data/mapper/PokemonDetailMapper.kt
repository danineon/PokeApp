package com.dgalan.pokeapp.pokemondetail.data.mapper

import com.dgalan.pokeapp.pokemondetail.data.model.PokemonDetailDTO
import com.dgalan.pokeapp.pokemondetail.data.model.StatDTO
import com.dgalan.pokeapp.pokemondetail.data.model.StatXDTO
import com.dgalan.pokeapp.pokemondetail.data.model.TypeDTO
import com.dgalan.pokeapp.pokemondetail.data.model.TypeXDTO
import com.dgalan.pokeapp.pokemondetail.domain.model.PokemonDetail
import com.dgalan.pokeapp.pokemondetail.domain.model.Stat
import com.dgalan.pokeapp.pokemondetail.domain.model.StatX
import com.dgalan.pokeapp.pokemondetail.domain.model.Type
import com.dgalan.pokeapp.pokemondetail.domain.model.TypeX
import com.dgalan.pokeapp.utils.toCapitalize

fun PokemonDetailDTO.toPokemonDetail(): PokemonDetail {
    return PokemonDetail(
        id = id,
        name = name.toCapitalize(),
        stats = stats.map { it.toStats() },
        types = types.map { it.toType() }
    )
}

private fun TypeDTO.toType(): Type {
    return Type(type = type.toTypeX())
}

private fun TypeXDTO.toTypeX(): TypeX {
    return TypeX(name = name)
}

private fun StatDTO.toStats(): Stat {
    return Stat(
        baseStat = baseStat,
        stat = stat.toStatX()
    )
}

private fun StatXDTO.toStatX(): StatX {
    return StatX(name = name)
}
