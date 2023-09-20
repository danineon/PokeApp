package com.dgalan.pokeapp.ui.designsystem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.dgalan.pokeapp.ui.theme.AppTypography

@Composable
fun DSTextField(
    inputText: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    iconContentDescription: String
) {
    TextField(
        value = inputText,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp)
            .clip(RoundedCornerShape(25.dp)),
        label = {
            Text(
                text = label
            )
        },
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = iconContentDescription,
                tint = Color.White,
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        textStyle = AppTypography.bodyMedium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF424242),
            unfocusedContainerColor = Color(0xFF424242),
            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,
            cursorColor = Color(0xFFED1A25),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}