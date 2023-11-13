package com.dgalan.pokeapp.pokemondetail.ui.tab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dgalan.pokeapp.pokemondetail.ui.state.PokemonDetailUIState
import com.dgalan.pokeapp.ui.designsystem.DSStat

@Composable
fun StatsTab(pokemonDetailUIState: PokemonDetailUIState) {
    val statsList = pokemonDetailUIState.run {
        listOf(
            Triple("PS", baseStats[0], highlights[0]),
            Triple("Attack", baseStats[1], highlights[1]),
            Triple("Defense", baseStats[2], highlights[2]),
            Triple("Sp. Atk", baseStats[3], highlights[3]),
            Triple("Sp. Def", baseStats[4], highlights[4]),
            Triple("Speed", baseStats[5], highlights[5])
        )
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 16.dp)
    ) {
        statsList.forEach { (stat, value, highlight) ->
            DSStat(
                stat = stat,
                value = value,
                highlight = highlight
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF111111)
@Composable
fun StatsTabPrev() {
    StatsTab(
        pokemonDetailUIState = PokemonDetailUIState(
            id = 1,
            name = "Bulbasaur",
            baseStats = listOf("45", "49", "49", "65", "65", "45"),
            highlights = listOf(false, false, false, true, true, false),
            types = listOf("grass", "poison")
        )
    )
}