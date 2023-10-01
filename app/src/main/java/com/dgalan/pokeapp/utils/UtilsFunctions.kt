package com.dgalan.pokeapp.utils

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

@Composable
fun DisableBackOnInitScreen() {
    var isBackDisabled by remember { mutableStateOf(false) }
    BackHandler(!isBackDisabled) {}
    LaunchedEffect(Unit) {
        delay(700)
        isBackDisabled = true
    }
}
