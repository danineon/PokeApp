package com.dgalan.pokeapp.register.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgalan.pokeapp.firebasedata.repository.FirebaseAuthRepository
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.ConfirmPasswordChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.ConfirmPasswordVisibilityChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.EmailChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.NameChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.PasswordChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.PasswordVisibilityChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.RegisterButtonClicked
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.ResetResourceState
import com.dgalan.pokeapp.register.ui.state.RegisterUIState
import com.dgalan.pokeapp.utils.di.CoroutineDispatcherModule.IoDispatcher
import com.dgalan.pokeapp.utils.state.Resource
import com.dgalan.pokeapp.utils.state.Resource.Idle
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _registerUIState = MutableStateFlow(RegisterUIState())
    val registerUIState: StateFlow<RegisterUIState> = _registerUIState.asStateFlow()

    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>>(Idle)
    val registerFlow: StateFlow<Resource<FirebaseUser>> = _registerFlow.asStateFlow()

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is NameChanged ->
                _registerUIState.value = _registerUIState.value.copy(name = event.name)

            is EmailChanged -> {
                _registerUIState.value = _registerUIState.value.copy(email = event.email)
                if (isLoginButtonClicked1stTime()) {
                    validateEmail()
                }
            }

            is PasswordChanged -> {
                _registerUIState.value = _registerUIState.value.copy(password = event.password)
                if (isLoginButtonClicked1stTime()) {
                    validatePassword()
                    validateMatchesPasswords()
                }
            }

            is PasswordVisibilityChanged ->
                _registerUIState.value = _registerUIState.value.copy(isPasswordVisible = event.isPasswordVisible)

            is ConfirmPasswordChanged -> {
                _registerUIState.value = _registerUIState.value.copy(confirmPassword = event.confirmPassword)
                if (isLoginButtonClicked1stTime()) {
                    validateMatchesPasswords()
                }
            }

            is ConfirmPasswordVisibilityChanged ->
                _registerUIState.value =
                    _registerUIState.value.copy(isConfirmPasswordVisible = event.isConfirmPasswordVisible)

            is RegisterButtonClicked -> {
                if (!isLoginButtonClicked1stTime()) {
                    _registerUIState.value = _registerUIState.value.copy(isLoginButtonClicked1stTime = true)
                }
                tryRegisterUser()
            }

            is ResetResourceState ->
                _registerFlow.value = Idle
        }
    }

    private fun isLoginButtonClicked1stTime(): Boolean {
        return _registerUIState.value.isLoginButtonClicked1stTime
    }

    private fun tryRegisterUser() {
        if (isValidEmail() && isValidPassword() && isValidMatchesPassword()) {
            registerUIState.value.run {
                registerUserInFirebase(name, email, password)
            }
        } else {
            showErrorsInTextFields()
        }
    }

    private fun isValidMatchesPassword(): Boolean =
        _registerUIState.value.password == _registerUIState.value.confirmPassword

    private fun isValidPassword(): Boolean =
        _registerUIState.value.password.length >= 6

    private fun showErrorsInTextFields() {
        if (!isValidEmail()) {
            _registerUIState.value = _registerUIState.value.copy(isValidEmail = false)
        }
        if (!isValidPassword()) {
            _registerUIState.value = _registerUIState.value.copy(isValidPassword = false)
        }
        if (!isValidMatchesPassword()) {
            _registerUIState.value = _registerUIState.value.copy(isValidMatchesPassword = false)
        }
    }

    private fun validateMatchesPasswords() {
        if (isValidMatchesPassword()) {
            _registerUIState.value = _registerUIState.value.copy(isValidMatchesPassword = true)
        } else {
            _registerUIState.value = _registerUIState.value.copy(isValidMatchesPassword = false)
        }
    }

    private fun validatePassword() {
        if (isValidPassword()) {
            _registerUIState.value = _registerUIState.value.copy(isValidPassword = true)
        } else {
            _registerUIState.value = _registerUIState.value.copy(isValidPassword = false)
        }
    }

    private fun validateEmail() {
        if (isValidEmail()) {
            _registerUIState.value = _registerUIState.value.copy(isValidEmail = true)
        } else {
            _registerUIState.value = _registerUIState.value.copy(isValidEmail = false)
        }
    }

    private fun isValidEmail(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(registerUIState.value.email.trim()).matches()

    private fun registerUserInFirebase(name: String, email: String, password: String) =
        viewModelScope.launch(ioDispatcher) {
            _registerFlow.value = Resource.Loading
            val result = firebaseAuthRepository.registerUserInFirebase(name, email, password)
            _registerFlow.value = result
        }
}