package com.dgalan.pokeapp.ui.designsystem

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dgalan.pokeapp.ui.theme.AppTypography
import com.dgalan.pokeapp.utils.ANIMATION_PROGRESS_DURATION
import com.dgalan.pokeapp.utils.toLinearProgressBar

@Composable
fun DSStat(
    stat: String,
    value: String,
    highlight: Boolean,
    animationPlayed: Boolean,
    onAnimationPlayedEvent: () -> Unit

) {
    val progress = remember { Animatable(0.0f) }

    LaunchedEffect(animationPlayed) {
        if (!animationPlayed) {
            progress.animateTo(
                targetValue = value.toLinearProgressBar(),
                animationSpec = tween(
                    durationMillis = ANIMATION_PROGRESS_DURATION,
                    easing = FastOutSlowInEasing
                )
            )
            onAnimationPlayedEvent()
        }
    }

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stat,
            style = AppTypography.bodyMedium,
            color = Color(0xFFC4C4C4),
            modifier = Modifier
                .width(72.dp)
                .padding(bottom = 8.dp)
        )
        Text(
            text = value,
            style = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color(0xFF2DB485).takeIf { highlight } ?: Color(0xFFCFCFCF)
        )
        LinearProgressIndicator(
            progress = progress.value.takeIf { !animationPlayed } ?: value.toLinearProgressBar(),
            color = Color(0xFF2DB485).takeIf { highlight } ?: Color(0xFFCFCFCF),
            trackColor = Color(0xFFA0A0A0).copy(alpha = 0.2f),
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .height(8.dp)
                .width(140.dp)
                .clip(shape = RoundedCornerShape(64.dp))
        )
    }
}
