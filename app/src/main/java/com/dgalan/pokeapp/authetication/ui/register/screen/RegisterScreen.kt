package com.dgalan.pokeapp.authetication.ui.register.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.dgalan.pokeapp.R.string
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.ConfirmPasswordChanged
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.ConfirmPasswordVisibilityChanged
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.EmailChanged
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.NameChanged
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.PasswordChanged
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.PasswordVisibilityChanged
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.RegisterButtonClicked
import com.dgalan.pokeapp.authetication.ui.register.state.RegisterUIEvent.ResetResourceState
import com.dgalan.pokeapp.authetication.ui.register.viewmodel.RegisterViewModel
import com.dgalan.pokeapp.ui.designsystem.DSButton
import com.dgalan.pokeapp.ui.designsystem.DSLoadingDialog
import com.dgalan.pokeapp.ui.designsystem.DSText
import com.dgalan.pokeapp.ui.designsystem.DSTextField
import com.dgalan.pokeapp.ui.theme.AppTypography
import com.dgalan.pokeapp.utils.DisableBackOnInitScreen
import com.dgalan.pokeapp.utils.state.Resource.Error
import com.dgalan.pokeapp.utils.state.Resource.Idle
import com.dgalan.pokeapp.utils.state.Resource.Loading
import com.dgalan.pokeapp.utils.state.Resource.Success

@Composable
fun RegisterScreen(navController: NavController, registerViewModel: RegisterViewModel = hiltViewModel()) {
    val registerUIState by registerViewModel.registerUIState.collectAsStateWithLifecycle()
    val registerFlow by registerViewModel.registerFlow.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    DisableBackOnInitScreen()
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF111111)),
    ) {
        Spacer(modifier = Modifier.size(24.dp))
        DSText(
            text = stringResource(string.register),
            style = AppTypography.headlineMedium
        )
        Spacer(modifier = Modifier.size(24.dp))
        NameField(
            name = registerUIState.name,
            onEmailValueChange = { registerViewModel.onEvent(NameChanged(it)) },
            onKeyBoardDone = { focusManager.moveFocus(FocusDirection.Down) }
        )
        Spacer(modifier = Modifier.size(8.dp))
        EmailField(
            email = registerUIState.email,
            onEmailValueChange = { registerViewModel.onEvent(EmailChanged(it)) },
            onKeyBoardDone = { focusManager.moveFocus(FocusDirection.Down) },
            isValidaEmail = registerUIState.isValidEmail
        )
        Spacer(modifier = Modifier.size(8.dp))
        PasswordField(
            password = registerUIState.password,
            onPasswordValueChange = { registerViewModel.onEvent(PasswordChanged(it)) },
            trailingIconOnClick = { registerViewModel.onEvent(PasswordVisibilityChanged(!registerUIState.isPasswordVisible)) },
            isPasswordVisible = registerUIState.isPasswordVisible,
            label = { PasswordLabel() },
            onKeyBoardDone = { focusManager.moveFocus(FocusDirection.Down) },
            isError = !registerUIState.isValidPassword
        )
        Spacer(modifier = Modifier.size(8.dp))
        PasswordField(
            password = registerUIState.confirmPassword,
            onPasswordValueChange = { registerViewModel.onEvent(ConfirmPasswordChanged(it)) },
            trailingIconOnClick = { registerViewModel.onEvent(ConfirmPasswordVisibilityChanged(!registerUIState.isConfirmPasswordVisible)) },
            isPasswordVisible = registerUIState.isConfirmPasswordVisible,
            label = { ConfirmPasswordLabel() },
            onKeyBoardDone = { focusManager.clearFocus() },
            isError = !registerUIState.isValidMatchesPassword
        )
        Spacer(modifier = Modifier.size(24.dp))
        RegisterButton(registerOnClick = { registerViewModel.onEvent(RegisterButtonClicked) })

        when (registerFlow) {
            is Error -> {
                LaunchedEffect(key1 = registerFlow) {
                    registerViewModel.onEvent(ResetResourceState)
                    Toast.makeText(context, (registerFlow as Error).exception.message, Toast.LENGTH_LONG).show()
                }
            }

            is Loading -> {
                DSLoadingDialog()
            }

            is Success -> {
                LaunchedEffect(key1 = registerFlow) {
                    registerViewModel.onEvent(ResetResourceState)
                    Toast.makeText(context, "Register success", Toast.LENGTH_LONG).show()
                    navController.navigateUp()
                }
            }

            is Idle -> {
                /* do nothing */
            }
        }
    }
}

@Composable
fun NameField(
    name: String,
    onEmailValueChange: (String) -> Unit,
    onKeyBoardDone: (KeyboardActionScope) -> Unit
) {
    DSTextField(
        inputText = name,
        onValueChange = onEmailValueChange,
        label = { NameLabel() },
        leadingIcon = { NameLeadingIcon() },
        keyboardType = KeyboardType.Email,
        trailingIconOnClick = { /* No has trailing icon */ },
        onKeyboardDone = onKeyBoardDone,
        isError = false
    )
}

@Composable
fun EmailField(
    email: String,
    onEmailValueChange: (String) -> Unit,
    onKeyBoardDone: (KeyboardActionScope) -> Unit,
    isValidaEmail: Boolean
) {
    DSTextField(
        inputText = email,
        onValueChange = onEmailValueChange,
        label = { EmailLabel() },
        leadingIcon = { EmailLeadingIcon(!isValidaEmail) },
        keyboardType = KeyboardType.Email,
        trailingIconOnClick = { /* No has trailing icon */ },
        onKeyboardDone = onKeyBoardDone,
        isError = !isValidaEmail
    )
}

@Composable
fun PasswordField(
    password: String,
    onPasswordValueChange: (String) -> Unit,
    trailingIconOnClick: () -> Unit,
    isPasswordVisible: Boolean,
    label: @Composable () -> Unit,
    onKeyBoardDone: (KeyboardActionScope) -> Unit,
    isError: Boolean
) {
    DSTextField(
        inputText = password,
        onValueChange = onPasswordValueChange,
        label = label,
        leadingIcon = { PasswordLeadingIcon(isError) },
        keyboardType = KeyboardType.Password,
        trailingIconOnClick = trailingIconOnClick,
        isPasswordVisible = isPasswordVisible,
        onKeyboardDone = onKeyBoardDone,
        isError = isError
    )
}

@Composable
fun RegisterButton(registerOnClick: () -> Unit) {
    DSButton(
        onClick = registerOnClick,
        text = stringResource(string.sign_up)
    )
}

@Composable
fun NameLeadingIcon() {
    Icon(
        imageVector = Outlined.Person,
        contentDescription = stringResource(string.person_icon),
        tint = Color.White
    )
}

@Composable
fun EmailLeadingIcon(isError: Boolean) {
    Icon(
        imageVector = Outlined.Email,
        contentDescription = stringResource(string.email_icon),
        tint = Color.White.takeIf { !isError } ?: MaterialTheme.colorScheme.error
    )
}

@Composable
fun PasswordLeadingIcon(isError: Boolean) {
    Icon(
        imageVector = Outlined.Key,
        contentDescription = stringResource(string.password_icon),
        tint = Color.White.takeIf { !isError } ?: MaterialTheme.colorScheme.error
    )
}

@Composable
fun NameLabel() {
    Text(text = stringResource(string.name))
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
fun ConfirmPasswordLabel() {
    Text(text = stringResource(string.confirm_password))
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPrev() {
    RegisterScreen(rememberNavController())
}