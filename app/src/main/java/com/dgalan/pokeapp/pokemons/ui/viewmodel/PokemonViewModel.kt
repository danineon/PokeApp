package com.dgalan.pokeapp.pokemons.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dgalan.pokeapp.pokemons.data.paging.PokemonPagingSource
import com.dgalan.pokeapp.pokemons.domain.model.PokemonResult
import com.dgalan.pokeapp.utils.CALL_DELAY
import com.dgalan.pokeapp.utils.di.CoroutineDispatcherModule.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
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

    private val _pokemonPager = MutableStateFlow(PagingData.empty<PokemonResult>())
    val pokemonPager: StateFlow<PagingData<PokemonResult>> = _pokemonPager.asStateFlow()

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            pokemonPagingSource.getPokemonList().cachedIn(viewModelScope).collect { pagingData ->
                _pokemonPager.value = pagingData
                delay(CALL_DELAY)
                _state.value = _state.value.copy(loading = false)
            }
        }
    }

    data class UiState(
        val loading: Boolean = false
    )
}
