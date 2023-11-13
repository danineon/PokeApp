package com.dgalan.pokeapp.ui.designsystem

import android.os.SystemClock
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dgalan.pokeapp.ui.theme.AppTypography

@Composable
fun DSButton(
    onClick: () -> Unit,
    text: String
) {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    Button(
        onClick = {
            if (SystemClock.elapsedRealtime() - lastClickTime > 1000) {
                lastClickTime = SystemClock.elapsedRealtime()
                onClick()
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 72.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFED1A25),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            style = AppTypography.bodyLarge,
        )
    }
}