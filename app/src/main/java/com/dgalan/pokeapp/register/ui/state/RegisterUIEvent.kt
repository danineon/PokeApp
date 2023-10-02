package com.dgalan.pokeapp.register.ui.state

sealed class RegisterUIEvent {
    data class NameChanged(val name: String) : RegisterUIEvent()
    data class EmailChanged(val email: String) : RegisterUIEvent()
    data class PasswordChanged(val password: String) : RegisterUIEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterUIEvent()
    data class PasswordVisibilityChanged(val isPasswordVisible: Boolean) : RegisterUIEvent()
    data class ConfirmPasswordVisibilityChanged(val isConfirmPasswordVisible: Boolean) : RegisterUIEvent()
    data object RegisterButtonClicked : RegisterUIEvent()
    data object ResetResourceState : RegisterUIEvent()
}