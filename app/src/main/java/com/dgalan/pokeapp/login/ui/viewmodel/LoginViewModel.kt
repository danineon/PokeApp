package com.dgalan.pokeapp.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible.asStateFlow()

    fun onEmailValueChange(email: String) {
        _email.value = email
    }

    fun onPasswordValueChange(password: String) {
        _password.value = password
    }

    fun onVisibilityButtonClick() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }
}