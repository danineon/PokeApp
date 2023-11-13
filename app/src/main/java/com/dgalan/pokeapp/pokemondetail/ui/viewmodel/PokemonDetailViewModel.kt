package com.dgalan.pokeapp.pokemondetail.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgalan.pokeapp.pokemondetail.domain.DomainContract
import com.dgalan.pokeapp.pokemondetail.domain.DomainContract.Repository
import com.dgalan.pokeapp.pokemondetail.ui.state.PokemonDetailUIEvent
import com.dgalan.pokeapp.pokemondetail.ui.state.PokemonDetailUIEvent.InitPokemonDetail
import com.dgalan.pokeapp.pokemondetail.ui.state.PokemonDetailUIState
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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _pokemonDetailUIState = MutableStateFlow(PokemonDetailUIState())
    val pokemonDetailUIState: StateFlow<PokemonDetailUIState> = _pokemonDetailUIState.asStateFlow()

    fun onEvent(event: PokemonDetailUIEvent) {
        when (event) {
            is InitPokemonDetail -> {
                viewModelScope.launch(ioDispatcher) {
                    val pokemonDetail = pokemonDetailRepository.getPokemonDetail(event.pokemonId)
                    with(pokemonDetail) {
                        _pokemonDetailUIState.value = _pokemonDetailUIState.value.copy(
                            id = id,
                            name = name,
                            baseStats = stats.map { it.baseStat.toString() },
                            highlights = getHighlightsStatsUseCase.getHighlightsStats(stats),
                            types = types.map { it.type.name }
                        )
                    }

                }
            }
        }
    }
}