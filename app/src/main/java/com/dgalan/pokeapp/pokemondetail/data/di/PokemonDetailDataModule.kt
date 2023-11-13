package com.dgalan.pokeapp.pokemondetail.data.di

import com.dgalan.pokeapp.pokemondetail.data.datasource.PokemonDetailDataSource
import com.dgalan.pokeapp.pokemondetail.data.datasource.PokemonDetailDataSourceContract
import com.dgalan.pokeapp.pokemondetail.data.repository.PokemonDetailRepository
import com.dgalan.pokeapp.pokemondetail.domain.DomainContract.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PokemonDetailDataModule {

    @Singleton
    @Provides
    fun providesPokemonDetailDataSource(
        pokemonDetailDataSource: PokemonDetailDataSource
    ): PokemonDetailDataSourceContract.Remote {
        return pokemonDetailDataSource
    }

    @Singleton
    @Provides
    fun providesPokemonDetailRepository(
        pokemonDetailRepository: PokemonDetailRepository
    ): Repository {
        return pokemonDetailRepository
    }
}