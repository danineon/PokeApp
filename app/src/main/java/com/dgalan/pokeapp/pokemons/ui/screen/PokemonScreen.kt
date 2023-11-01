package com.dgalan.pokeapp.pokemons.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState.Loading
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.dgalan.pokeapp.R.drawable
import com.dgalan.pokeapp.R.string
import com.dgalan.pokeapp.pokemons.domain.model.PokemonResult
import com.dgalan.pokeapp.pokemons.ui.viewmodel.PokemonViewModel
import com.dgalan.pokeapp.ui.theme.AppTypography

@Composable
fun PokemonScreen(pokemonViewModel: PokemonViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val pokemonPager = pokemonViewModel.pokemonPager.collectAsLazyPagingItems()

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

        pokemonPager.loadState.run {
            if (refresh is Loading || append is Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Color(0xFFD51818)
                )
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = items[itemPosition]!!.name, style = AppTypography.bodyMedium)
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "#${itemPosition}",
                    style = AppTypography.labelMedium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Card {
                    Text(
                        modifier = Modifier.padding(horizontal = 6.dp),
                        text = "Fire",
                        style = AppTypography.labelMedium
                    )
                }
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .offset(x = 12.dp, y = 12.dp)
                            .rotate(-14.3f),
                        painter = painterResource(id = drawable.ic_pokeball_byn),
                        contentDescription = stringResource(string.pokeball_image)
                    )
                    AsyncImage(
                        modifier = Modifier.fillMaxHeight(),
                        model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${itemPosition + 1}.png",
                        contentDescription = stringResource(string.pokemon_image)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PokemonScreenPrev() {
    PokemonScreen()
}