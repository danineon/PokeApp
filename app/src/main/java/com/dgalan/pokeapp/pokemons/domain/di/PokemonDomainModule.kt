package com.dgalan.pokeapp.pokemons.domain.di

import com.dgalan.pokeapp.pokemons.data.repository.PokemonRepository
import com.dgalan.pokeapp.pokemons.domain.DomainContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PokemonDomainModule {

    @Singleton
    @Provides
    fun providesPokemonRepository(pokemonRepository: PokemonRepository): DomainContract.Repository {
        return pokemonRepository
    }
}