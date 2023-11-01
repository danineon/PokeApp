package com.dgalan.pokeapp.utils.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

fun Modifier.shimmer(
    duration: Int,
): Modifier = composed {
    val shimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = lazyListTheme(duration),
    )
    shimmer(customShimmer = shimmer)
}

private fun lazyListTheme(duration: Int) = defaultShimmerTheme.copy(
    animationSpec = infiniteRepeatable(
        animation = tween(
            durationMillis = duration,
            delayMillis = 200,
            easing = LinearEasing,
        )
    ),
    shimmerWidth = 150.dp,
)
