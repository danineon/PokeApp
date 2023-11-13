package com.dgalan.pokeapp.pokemondetail.domain.useCase

import com.dgalan.pokeapp.pokemondetail.domain.DomainContract
import com.dgalan.pokeapp.pokemondetail.domain.model.Stat
import javax.inject.Inject

class GetHighlightsStatsUseCase @Inject constructor() : DomainContract.UseCase {

    override suspend fun getHighlightsStats(stats: List<Stat>): List<Boolean> {
        val twoLargestNumbers = stats.map { it.baseStat }.sortedDescending().take(2)
        return stats.map { it.baseStat in twoLargestNumbers }
    }
}