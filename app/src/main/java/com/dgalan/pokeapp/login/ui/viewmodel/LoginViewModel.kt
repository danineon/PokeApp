package com.dgalan.pokeapp.login.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgalan.pokeapp.firebasedata.repository.FirebaseAuthRepository
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.EmailChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.LoginButtonClicked
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.PasswordChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.PasswordVisibilityChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.ResetResourceState
import com.dgalan.pokeapp.login.ui.state.LoginUIState
import com.dgalan.pokeapp.utils.state.Resource
import com.dgalan.pokeapp.utils.state.Resource.Idle
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _loginUIState = MutableStateFlow(LoginUIState())
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>>(Idle)
    val loginFlow: StateFlow<Resource<FirebaseUser>> = _loginFlow.asStateFlow()

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is EmailChanged ->
                _loginUIState.value = _loginUIState.value.copy(email = event.email)

            is PasswordChanged ->
                _loginUIState.value = _loginUIState.value.copy(password = event.password)

            is PasswordVisibilityChanged ->
                _loginUIState.value = _loginUIState.value.copy(isPasswordVisible = event.isPasswordVisible)

            is LoginButtonClicked ->
                loginUIState.value.run {
                    loginUserInFirebase(email, password)
                }

            is ResetResourceState ->
                _loginFlow.value = Idle
        }
    }

    private fun loginUserInFirebase(email: String, password: String) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading
        val result = firebaseAuthRepository.loginUserInFirebase(email, password)
        _loginFlow.value = result
    }
}