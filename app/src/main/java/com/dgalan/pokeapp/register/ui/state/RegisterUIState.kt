package com.dgalan.pokeapp.register.ui.state

data class RegisterUIState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isValidEmail: Boolean = true,
    val isValidPassword: Boolean = true,
    val isValidMatchesPassword: Boolean = true,
    val isLoginButtonClicked1stTime: Boolean = false
)
