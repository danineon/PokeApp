package com.dgalan.pokeapp.pokemondetail.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgalan.pokeapp.pokemondetail.domain.DomainContract
import com.dgalan.pokeapp.pokemondetail.domain.DomainContract.Repository
import com.dgalan.pokeapp.utils.di.CoroutineDispatcherModule.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonDetailRepository: Repository,
    private val getHighlightsStatsUseCase: DomainContract.UseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId = savedStateHandle["pokemonId"] ?: 0

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            _state.value = _state.value.copy(loading = true)
            val pokemonDetail = pokemonDetailRepository.getPokemonDetail(pokemonId)
            with(pokemonDetail) {
                _state.value = _state.value.copy(
                    id = id,
                    name = name,
                    baseStats = stats.map { it.baseStat.toString() },
                    highlights = getHighlightsStatsUseCase.getHighlightsStats(stats),
                    types = types.map { it.type.name }
                )
            }
            _state.value = _state.value.copy(loading = false)
        }
    }

    data class UiState(
        val id: Int = 0,
        val name: String = "",
        val baseStats: List<String> = emptyList(),
        val highlights: List<Boolean> = emptyList(),
        val types: List<String> = emptyList(),
        val loading: Boolean = false
    )
}