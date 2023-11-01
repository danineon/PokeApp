package com.dgalan.pokeapp.authetication.ui.login.state

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)
