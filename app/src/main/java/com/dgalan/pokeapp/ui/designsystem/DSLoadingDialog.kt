package com.dgalan.pokeapp.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dgalan.pokeapp.R
import com.dgalan.pokeapp.ui.theme.AppTypography

@Composable
fun DSLoadingDialog() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_pokeball_loading))
    Dialog(
        onDismissRequest = {},
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(110.dp)
                .background(
                    color = Color.Black,
                    shape = RoundedCornerShape(30.dp)
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    modifier = Modifier.size(48.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever
                )
                Spacer(modifier = Modifier.size(8.dp))
                DSText(text = "Loading", style = AppTypography.bodySmall)
            }
        }
    }
}