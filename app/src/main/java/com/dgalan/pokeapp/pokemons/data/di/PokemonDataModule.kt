package com.dgalan.pokeapp.pokemons.data.di

import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSource
import com.dgalan.pokeapp.pokemons.data.datasource.PokemonDataSourceContract
import com.dgalan.pokeapp.pokemons.data.repository.PokemonRepository
import com.dgalan.pokeapp.pokemons.domain.DomainContract
import com.dgalan.pokeapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PokemonDataModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun providesPokemonDatasource(pokemonDataSource: PokemonDataSource): PokemonDataSourceContract.Remote =
        pokemonDataSource

    @Singleton
    @Provides
    fun providesPokemonRepository(pokemonRepository: PokemonRepository): DomainContract.Repository {
        return pokemonRepository
    }
}