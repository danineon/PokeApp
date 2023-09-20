package com.dgalan.pokeapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dgalan.pokeapp.R
import com.dgalan.pokeapp.R.string
import com.dgalan.pokeapp.ui.designsystem.DSTextField
import com.dgalan.pokeapp.ui.theme.AppTypography

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF111111)),
    ) {
        LogoImage()
        Spacer(modifier = Modifier.size(24.dp))
        AppName()
        Spacer(modifier = Modifier.size(24.dp))
        EmailField(
            email,
            onEmailValueChange = { email = it }
        )
        Spacer(modifier = Modifier.size(8.dp))
        PasswordField(
            password,
            onPasswordValueChange = { password = it }
        )
        Spacer(modifier = Modifier.size(24.dp))
        LoginButton()
    }
}

@Composable
fun LogoImage() {
    Image(
        painter = painterResource(id = R.drawable.ic_logo_pokeball),
        contentDescription = stringResource(string.pokeball_logo),
        Modifier
            .offset(
                x = LocalConfiguration.current.screenWidthDp.dp / 2,
                y = LocalConfiguration.current.screenHeightDp.dp / 30
            )
            .rotate(-14.3f)
    )
}

@Composable
fun AppName() {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color(0xFFED1A25))) {
            append(stringResource(string.poke))
        }
        withStyle(style = SpanStyle(color = Color.White)) {
            append(stringResource(string.app))
        }
    }
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 64.sp,
        fontFamily = FontFamily(Font(R.font.pokemon_solid))
    )
}

@Composable
fun EmailField(
    email: String,
    onEmailValueChange: (String) -> Unit,
    emailLabel: String = stringResource(string.email),
    emailLeadingIcon: ImageVector = Icons.Outlined.Email,
    emailIconContentDescription: String = stringResource(string.email_icon)
) {
    DSTextField(
        inputText = email,
        onValueChange = onEmailValueChange,
        label = emailLabel,
        leadingIcon = emailLeadingIcon,
        iconContentDescription = emailIconContentDescription
    )
}

@Composable
fun PasswordField(
    password: String,
    onPasswordValueChange: (String) -> Unit,
    passwordLabel: String = stringResource(string.password),
    passwordLeadingIcon: ImageVector = Icons.Outlined.Key,
    passwordIconContentDescription: String = stringResource(string.password_icon)
) {
    DSTextField(
        inputText = password,
        onValueChange = onPasswordValueChange,
        label = passwordLabel,
        leadingIcon = passwordLeadingIcon,
        iconContentDescription = passwordIconContentDescription
    )
}

@Composable
fun LoginButton() {
    Button(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFED1A25),
            contentColor = Color.White
        )
    ) {
        Text(
            text = stringResource(string.log_in),
            style = AppTypography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPrev() {
    LoginScreen()
}