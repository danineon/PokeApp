package com.dgalan.pokeapp.pokemons.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dgalan.pokeapp.pokemons.data.paging.PokemonPagingSource
import com.dgalan.pokeapp.pokemons.domain.model.PokemonResult
import com.dgalan.pokeapp.utils.di.CoroutineDispatcherModule.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CALL_DELAY = 1000L

@HiltViewModel
class PokemonViewModel @Inject constructor(
    pokemonPagingSource: PokemonPagingSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _pokemonPager = MutableStateFlow<PagingData<PokemonResult>>(PagingData.empty())
    val pokemonPager: StateFlow<PagingData<PokemonResult>> = _pokemonPager.asStateFlow()

    init {
        viewModelScope.launch {
            Pager(
                config = PagingConfig(pageSize = 20),
                pagingSourceFactory = { pokemonPagingSource }
            ).flow.collect { pagingData ->
                _pokemonPager.value = pagingData
            }
        }
    }
}
