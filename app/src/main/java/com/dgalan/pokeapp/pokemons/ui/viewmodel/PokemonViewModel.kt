package com.dgalan.pokeapp.pokemons.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dgalan.pokeapp.pokemons.data.paging.PokemonPagingSource
import com.dgalan.pokeapp.pokemons.domain.model.PokemonResult
import com.dgalan.pokeapp.pokemons.ui.state.PokemonUIEvent
import com.dgalan.pokeapp.pokemons.ui.state.PokemonUIEvent.IsShimmerShown
import com.dgalan.pokeapp.utils.di.CoroutineDispatcherModule.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    pokemonPagingSource: PokemonPagingSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _pokemonPager = MutableStateFlow<PagingData<PokemonResult>>(PagingData.empty())
    val pokemonPager: StateFlow<PagingData<PokemonResult>> = _pokemonPager.asStateFlow()

    private val _isShimmerShown = MutableStateFlow(false)
    val isShimmerShown: StateFlow<Boolean> = _isShimmerShown.asStateFlow()

    init {
        viewModelScope.launch {
            pokemonPagingSource.getPokemonList().cachedIn(viewModelScope).collect { pagingData ->
                _pokemonPager.value = pagingData
            }
        }
    }

    fun onEvent(event: PokemonUIEvent) {
        when (event) {
            is IsShimmerShown -> _isShimmerShown.value = true
        }
    }
}
