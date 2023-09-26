package com.dgalan.pokeapp.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.dgalan.pokeapp.R
import com.dgalan.pokeapp.login.ui.screen.LoginScreen
import com.dgalan.pokeapp.ui.theme.AppTypography

@Composable
fun DSTextField(
    inputText: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit),
    leadingIcon: @Composable (() -> Unit),
    keyboardType: KeyboardType,
    trailingIconOnClick: () -> Unit,
    isPasswordVisible: Boolean = false
) {
    val isPasswordField = keyboardType == KeyboardType.Password
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        value = inputText,
        onValueChange = onValueChange,
        colors = textFieldColors(),
        shape = RoundedCornerShape(20.dp),
        label = label,
        leadingIcon = leadingIcon,
        textStyle = AppTypography.bodyMedium,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation(isPasswordField, isPasswordVisible),
        trailingIcon = { if (isPasswordField) TrailingIcon(trailingIconOnClick, isPasswordVisible) }
    )
}

@Composable
private fun TrailingIcon(
    trailingIconOnClick: () -> Unit,
    isPasswordVisible: Boolean
) {
    IconButton(
        onClick = trailingIconOnClick,
        modifier = Modifier.padding(end = 12.dp)
    ) {
        Icon(
            imageVector = Outlined.VisibilityOff.takeIf { !isPasswordVisible } ?: Outlined.Visibility,
            contentDescription = stringResource(R.string.visibility_off_icon.takeIf { !isPasswordVisible }
                ?: R.string.visibility_icon),
            tint = Color.Black
        )
    }
}

@Composable
private fun visualTransformation(isPasswordField: Boolean, isPasswordVisible: Boolean): VisualTransformation {
    return PasswordVisualTransformation(mask = '*').takeIf { isPasswordField && !isPasswordVisible }
        ?: VisualTransformation.None
}

@Composable
private fun textFieldColors(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = Color(0xFF424242),
        unfocusedContainerColor = Color(0xFF424242),
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.White,
        cursorColor = Color.White,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        focusedTextColor = Color.White,
        unfocusedTextColor = Color.White
    )
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPrev() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LoginScreen(rememberNavController())
    }
}