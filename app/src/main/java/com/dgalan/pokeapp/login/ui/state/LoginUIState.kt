package com.dgalan.pokeapp.login.ui.state

data class LoginUIState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false
)
