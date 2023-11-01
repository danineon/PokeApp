package com.dgalan.pokeapp.authetication.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgalan.pokeapp.authetication.data.repository.FirebaseAuthRepository
import com.dgalan.pokeapp.authetication.ui.login.state.LoginUIEvent
import com.dgalan.pokeapp.authetication.ui.login.state.LoginUIEvent.EmailChanged
import com.dgalan.pokeapp.authetication.ui.login.state.LoginUIEvent.LoginButtonClicked
import com.dgalan.pokeapp.authetication.ui.login.state.LoginUIEvent.PasswordChanged
import com.dgalan.pokeapp.authetication.ui.login.state.LoginUIEvent.PasswordVisibilityChanged
import com.dgalan.pokeapp.authetication.ui.login.state.LoginUIEvent.ResetResourceState
import com.dgalan.pokeapp.authetication.ui.login.state.LoginUIState
import com.dgalan.pokeapp.utils.di.CoroutineDispatcherModule.IoDispatcher
import com.dgalan.pokeapp.utils.state.Resource
import com.dgalan.pokeapp.utils.state.Resource.Idle
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val CALL_DELAY = 1000L

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
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

    private fun loginUserInFirebase(email: String, password: String) {
        viewModelScope.launch(ioDispatcher) {
            _loginFlow.value = Resource.Loading
            delay(CALL_DELAY)
            val result = firebaseAuthRepository.loginUserInFirebase(email, password)
            _loginFlow.value = result
        }
    }
}