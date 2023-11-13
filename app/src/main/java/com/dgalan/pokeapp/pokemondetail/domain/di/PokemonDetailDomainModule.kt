package com.dgalan.pokeapp.pokemondetail.domain.di

import com.dgalan.pokeapp.pokemondetail.domain.DomainContract
import com.dgalan.pokeapp.pokemondetail.domain.useCase.GetHighlightsStatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PokemonDetailDomainModule {

    @Singleton
    @Provides
    fun provideGetHighlightsStats(getHighlightsStats: GetHighlightsStatsUseCase): DomainContract.UseCase {
        return getHighlightsStats
    }
}