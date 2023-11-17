package com.dgalan.pokeapp.pokemondetail.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.dgalan.pokeapp.R.string
import com.dgalan.pokeapp.pokemondetail.ui.tab.StatsTab
import com.dgalan.pokeapp.pokemondetail.ui.viewmodel.PokemonDetailViewModel
import com.dgalan.pokeapp.ui.designsystem.DSLoadingDialog
import com.dgalan.pokeapp.ui.designsystem.DSPokemonType
import com.dgalan.pokeapp.ui.designsystem.DSText
import com.dgalan.pokeapp.ui.theme.AppTypography
import com.dgalan.pokeapp.utils.getPokemonImage
import kotlinx.coroutines.launch

data class TabItem(val title: String)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PokemonDetailScreen(pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()) {
    val tabItems = listOf(
        TabItem("Stats"),
        TabItem("Evolutions"),
        TabItem("Moves"),
        TabItem("Forms"),
        TabItem("Items")
    )
    val state by pokemonDetailViewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { tabItems.size }

    if (state.loading) {
        DSLoadingDialog()
    }

    if (state.id != 0) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF111111))
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            DSText(text = state.name, style = AppTypography.displaySmall)
            AsyncImage(
                modifier = Modifier.height(250.dp),
                model = getPokemonImage(state.id),
                contentDescription = stringResource(string.pokemon_image)
            )
            Row {
                state.types.forEach { type ->
                    DSPokemonType(type = type)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                indicator = { tabPositions -> CustomIndicator(tabPositions, pagerState) },
                divider = { CustomDivider() },
                edgePadding = 0.dp
            ) {
                tabItems.forEachIndexed { index, tabItem ->
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        unselectedContentColor = Color(0xFF777777),
                        selectedContentColor = Color.White,
                        text = { Text(text = tabItem.title, style = AppTypography.bodyLarge) }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { index ->
                when (index) {
                    0 -> StatsTab(state)
                    else -> {
                        DSText(
                            text = tabItems[index].title,
                            style = AppTypography.bodyLarge
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun CustomDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(color = Color(0xFF4D4D4D))
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CustomIndicator(
    tabPositions: List<TabPosition>,
    pagerState: PagerState
) {
    TabRowDefaults.Indicator(
        modifier = Modifier.tabIndicatorOffset(
            currentTabPosition = tabPositions[pagerState.currentPage],
        ),
        color = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailScreenPreview() {
    PokemonDetailScreen()
}