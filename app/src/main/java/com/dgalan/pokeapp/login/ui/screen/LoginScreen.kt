package com.dgalan.pokeapp.login.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dgalan.pokeapp.R
import com.dgalan.pokeapp.R.string
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.EmailChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.LoginButtonClicked
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.PasswordChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.PasswordVisibilityChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.ResetResourceState
import com.dgalan.pokeapp.login.ui.viewmodel.LoginViewModel
import com.dgalan.pokeapp.ui.designsystem.DSLoadingDialog
import com.dgalan.pokeapp.ui.designsystem.DSTextField
import com.dgalan.pokeapp.ui.theme.AppTypography
import com.dgalan.pokeapp.utils.state.Resource.Error
import com.dgalan.pokeapp.utils.state.Resource.Idle
import com.dgalan.pokeapp.utils.state.Resource.Loading
import com.dgalan.pokeapp.utils.state.Resource.Success

@Composable
fun LoginScreen() {
    val loginViewModel = hiltViewModel<LoginViewModel>()
    val loginUIState by loginViewModel.loginUIState.collectAsStateWithLifecycle()
    val loginFlow by loginViewModel.loginFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
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
            email = loginUIState.email,
            onEmailValueChange = { loginViewModel.onEvent(EmailChanged(it)) }
        )
        Spacer(modifier = Modifier.size(8.dp))
        PasswordField(
            password = loginUIState.password,
            onPasswordValueChange = { loginViewModel.onEvent(PasswordChanged(it)) },
            trailingIconOnClick = { loginViewModel.onEvent(PasswordVisibilityChanged(!loginUIState.isPasswordVisible)) },
            isPasswordVisible = loginUIState.isPasswordVisible
        )
        Spacer(modifier = Modifier.size(24.dp))
        LoginButton(loginOnClick = { loginViewModel.onEvent(LoginButtonClicked) })

        when (loginFlow) {
            is Error -> {
                Toast.makeText(context, (loginFlow as Error).exception.message, Toast.LENGTH_LONG).show()
                loginViewModel.onEvent(ResetResourceState)
            }

            is Loading -> {
                DSLoadingDialog()
            }

            is Success -> {
                Toast.makeText(context, "Login success", Toast.LENGTH_LONG).show()
                loginViewModel.onEvent(ResetResourceState)
            }

            is Idle -> { /* do nothing */
            }
        }
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
    onEmailValueChange: (String) -> Unit
) {
    DSTextField(
        inputText = email,
        onValueChange = onEmailValueChange,
        label = { EmailLabel() },
        leadingIcon = { EmailLeadingIcon() },
        keyboardType = KeyboardType.Email,
        trailingIconOnClick = { /* No has trailing icon */ }
    )
}

@Composable
fun PasswordField(
    password: String,
    onPasswordValueChange: (String) -> Unit,
    trailingIconOnClick: () -> Unit,
    isPasswordVisible: Boolean
) {
    DSTextField(
        inputText = password,
        onValueChange = onPasswordValueChange,
        label = { PasswordLabel() },
        leadingIcon = { PasswordLeadingIcon() },
        keyboardType = KeyboardType.Password,
        trailingIconOnClick = trailingIconOnClick,
        isPasswordVisible = isPasswordVisible
    )
}

@Composable
fun LoginButton(loginOnClick: () -> Unit) {
    Button(
        onClick = loginOnClick,
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
            text = stringResource(string.log_in),
            style = AppTypography.bodyMedium,
        )
    }
}

@Composable
fun EmailLeadingIcon() {
    Icon(
        imageVector = Outlined.Email,
        contentDescription = stringResource(string.email_icon),
        tint = Color.White
    )
}

@Composable
fun PasswordLeadingIcon() {
    Icon(
        imageVector = Outlined.Key,
        contentDescription = stringResource(string.password_icon),
        tint = Color.White
    )
}

@Composable
fun EmailLabel() {
    Text(text = stringResource(string.email))
}

@Composable
fun PasswordLabel() {
    Text(text = stringResource(string.password))
}

@Composable
fun EmailSupportingText() {
    Text(text = stringResource(string.this_is_an_invalid_email))
}

@Composable
fun PasswordSupportingText() {
    Text(text = stringResource(string.the_password_must_be_at_least_6_characters_long))
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPrev() {
    LoginScreen()
}