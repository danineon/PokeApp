package com.dgalan.pokeapp.ui.designsystem

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dgalan.pokeapp.R.drawable
import com.dgalan.pokeapp.R.string
import com.dgalan.pokeapp.ui.designsystem.PokemonType.BUG
import com.dgalan.pokeapp.ui.designsystem.PokemonType.DARK
import com.dgalan.pokeapp.ui.designsystem.PokemonType.DRAGON
import com.dgalan.pokeapp.ui.designsystem.PokemonType.ELECTRIC
import com.dgalan.pokeapp.ui.designsystem.PokemonType.FAIRY
import com.dgalan.pokeapp.ui.designsystem.PokemonType.FIGHTING
import com.dgalan.pokeapp.ui.designsystem.PokemonType.FIRE
import com.dgalan.pokeapp.ui.designsystem.PokemonType.FLYING
import com.dgalan.pokeapp.ui.designsystem.PokemonType.GHOST
import com.dgalan.pokeapp.ui.designsystem.PokemonType.GRASS
import com.dgalan.pokeapp.ui.designsystem.PokemonType.GROUND
import com.dgalan.pokeapp.ui.designsystem.PokemonType.ICE
import com.dgalan.pokeapp.ui.designsystem.PokemonType.NORMAL
import com.dgalan.pokeapp.ui.designsystem.PokemonType.POISON
import com.dgalan.pokeapp.ui.designsystem.PokemonType.PSYCHIC
import com.dgalan.pokeapp.ui.designsystem.PokemonType.ROCK
import com.dgalan.pokeapp.ui.designsystem.PokemonType.STEEL
import com.dgalan.pokeapp.ui.designsystem.PokemonType.WATER
import com.dgalan.pokeapp.ui.theme.AppTypography

enum class PokemonType(val value: String) {
    GRASS("grass"),
    POISON("poison"),
    FIRE("fire"),
    FLYING("flying"),
    WATER("water"),
    BUG("bug"),
    NORMAL("normal"),
    ELECTRIC("electric"),
    GROUND("ground"),
    FAIRY("fairy"),
    FIGHTING("fighting"),
    PSYCHIC("psychic"),
    ROCK("rock"),
    STEEL("steel"),
    ICE("ice"),
    GHOST("ghost"),
    DRAGON("dragon"),
    DARK("dark");

    companion object {

        fun fromValue(value: String) = values().first { it.value == value }
    }
}

@Composable
fun DSPokemonType(type: String) {
    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = pokemonColorType(type),
                shape = RoundedCornerShape(64.dp)
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(1.dp))
        Image(
            modifier = Modifier.height(18.dp),
            painter = painterResource(id = pokemonTypeDrawable(type)),
            contentDescription = stringResource(string.pokemon_type)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = type,
            style = AppTypography.labelMedium,
            color = pokemonColorType(type),
        )
        Spacer(modifier = Modifier.width(1.dp))
    }
}

fun pokemonColorType(type: String): Color = when (PokemonType.fromValue(type)) {
    GRASS -> Color(0xFF63BB5A)
    POISON -> Color(0xFFB566CE)
    FIRE -> Color(0xFFfE9C54)
    FLYING -> Color(0xFF93AADE)
    WATER -> Color(0xFF3892DC)
    BUG -> Color(0xFF90C12C)
    NORMAL -> Color(0xFF9FA19F)
    ELECTRIC -> Color(0xFFFBD102)
    GROUND -> Color(0xFFE87236)
    FAIRY -> Color(0xFFFB89EB)
    FIGHTING -> Color(0xFFE0306A)
    PSYCHIC -> Color(0xFFFF6775)
    ROCK -> Color(0xFFC8B685)
    STEEL -> Color(0xFFC8B685)
    ICE -> Color(0xFF4DD1C0)
    GHOST -> Color(0xFF5469AC)
    DRAGON -> Color(0xFF0A6DC4)
    DARK -> Color(0xFF5A5466)
}

private fun pokemonTypeDrawable(type: String) = when (PokemonType.fromValue(type)) {
    GRASS -> drawable.ic_grass_type
    POISON -> drawable.ic_poison_type
    FIRE -> drawable.ic_fire_type
    FLYING -> drawable.ic_flying_type
    WATER -> drawable.ic_water_type
    BUG -> drawable.ic_bug_type
    NORMAL -> drawable.ic_normal_type
    ELECTRIC -> drawable.ic_electric_type
    GROUND -> drawable.ic_ground_type
    FAIRY -> drawable.ic_fairy_type
    FIGHTING -> drawable.ic_fighting_type
    PSYCHIC -> drawable.ic_psychic_type
    ROCK -> drawable.ic_rock_type
    STEEL -> drawable.ic_steel_type
    ICE -> drawable.ic_ice_type
    GHOST -> drawable.ic_ghost_type
    DRAGON -> drawable.ic_dragon_type
    DARK -> drawable.ic_dark_type
}