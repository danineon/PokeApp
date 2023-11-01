package com.dgalan.pokeapp.authetication.ui.login.state

sealed class LoginUIEvent {
    data class EmailChanged(val email: String) : LoginUIEvent()
    data class PasswordChanged(val password: String) : LoginUIEvent()
    data class PasswordVisibilityChanged(val isPasswordVisible: Boolean) : LoginUIEvent()
    data object LoginButtonClicked : LoginUIEvent()
    data object ResetResourceState : LoginUIEvent()
}