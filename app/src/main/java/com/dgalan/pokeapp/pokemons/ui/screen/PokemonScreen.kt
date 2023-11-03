package com.dgalan.pokeapp.pokemons.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.dgalan.pokeapp.R.drawable
import com.dgalan.pokeapp.R.string
import com.dgalan.pokeapp.pokemons.domain.model.PokemonResult
import com.dgalan.pokeapp.pokemons.ui.viewmodel.PokemonViewModel
import com.dgalan.pokeapp.ui.theme.AppTypography
import com.dgalan.pokeapp.utils.CALL_DELAY
import com.dgalan.pokeapp.utils.forwardingPainter
import com.dgalan.pokeapp.utils.shimmer.shimmer
import kotlinx.coroutines.delay

@Composable
fun PokemonScreen(pokemonViewModel: PokemonViewModel = hiltViewModel()) {
    val pokemonPager = pokemonViewModel.pokemonPager.collectAsLazyPagingItems()
    var loadingShimmer by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(CALL_DELAY)
        loadingShimmer = false
    }

    PokemonLazyVerticalGrid(pokemonPager, loadingShimmer)
}

@Composable
fun PokemonLazyVerticalGrid(pokemonPager: LazyPagingItems<PokemonResult>, loadingShimmer: Boolean) {
    if (loadingShimmer) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                contentPadding = PaddingValues(8.dp),
                columns = GridCells.Fixed(2),
                userScrollEnabled = false
            ) {
                items(count = 20) {
                    PokemonShimmerItem()
                }
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(8.dp),
                columns = GridCells.Fixed(2),
            ) {
                items(count = pokemonPager.itemCount) { itemPosition ->
                    PokemonItem(pokemonPager, itemPosition)
                }
            }
        }
    }
}

@Composable
fun PokemonItem(items: LazyPagingItems<PokemonResult>, itemPosition: Int) {
    Card(
        modifier = Modifier
            .height(135.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2B2B2B),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = items[itemPosition]!!.name, style = AppTypography.bodyMedium)

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .offset(x = 12.dp, y = 12.dp)
                        .rotate(-14.3f)
                        .align(Alignment.CenterEnd),
                    painter = painterResource(id = drawable.ic_pokeball_byn),
                    contentDescription = stringResource(string.pokeball_image)
                )
                AsyncImage(
                    modifier = Modifier.fillMaxHeight(),
                    placeholder = forwardingPainter(
                        painter = painterResource(id = drawable.ic_pokemon_silhouette2),
                        colorFilter = ColorFilter.tint(Color(0xFF6B6B6B))
                    ),
                    error = forwardingPainter(
                        painter = painterResource(id = drawable.ic_pokemon_silhouette2),
                        colorFilter = ColorFilter.tint(Color(0xFF6B6B6B))
                    ),
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${itemPosition + 1}.png",
                    contentDescription = stringResource(string.pokemon_image)
                )
            }

        }
    }
}

@Composable
fun PokemonShimmerItem() {
    Card(
        modifier = Modifier
            .height(135.dp)
            .padding(8.dp)
            .shimmer(500),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2B2B2B),
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 1.dp, top = 4.dp)
                    .width(90.dp)
                    .height(15.dp)
                    .background(
                        color = Color(0xFF6B6B6B),
                        shape = RoundedCornerShape(3.dp),
                    )
            )
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .offset(x = 12.dp, y = 12.dp)
                        .rotate(-14.3f)
                        .align(Alignment.CenterEnd),
                    painter = painterResource(id = drawable.ic_pokeball_byn),
                    contentDescription = stringResource(string.pokeball_image)
                )
                Image(
                    modifier = Modifier
                        .height(58.dp),
                    painter = painterResource(id = drawable.ic_pokemon_silhouette),
                    contentDescription = stringResource(string.pokeball_image),
                    colorFilter = ColorFilter.tint(Color(0xFF6B6B6B))
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PokemonScreenPrev() {
    PokemonShimmerItem()
}