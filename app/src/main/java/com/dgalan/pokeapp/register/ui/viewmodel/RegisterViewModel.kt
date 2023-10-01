package com.dgalan.pokeapp.register.ui.viewmodel

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
class RegisterViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _registerUIState = MutableStateFlow(RegisterUIState())
    val registerUIState: StateFlow<RegisterUIState> = _registerUIState.asStateFlow()

    private val _registerFlow = MutableStateFlow<Resource<FirebaseUser>>(Idle)
    val registerFlow: StateFlow<Resource<FirebaseUser>> = _registerFlow.asStateFlow()

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is NameChanged ->
                _registerUIState.value = _registerUIState.value.copy(name = event.name)

            is EmailChanged ->
                _registerUIState.value = _registerUIState.value.copy(email = event.email)

            is PasswordChanged ->
                _registerUIState.value = _registerUIState.value.copy(password = event.password)

            is PasswordVisibilityChanged ->
                _registerUIState.value = _registerUIState.value.copy(isPasswordVisible = event.isPasswordVisible)

            is ConfirmPasswordChanged ->
                _registerUIState.value = _registerUIState.value.copy(confirmPassword = event.confirmPassword)

            is ConfirmPasswordVisibilityChanged ->
                _registerUIState.value =
                    _registerUIState.value.copy(isConfirmPasswordVisible = event.isConfirmPasswordVisible)

            is RegisterButtonClicked ->
                registerUIState.value.run {
                    registerUserInFirebase(name, email, password)
                }

            is ResetResourceState ->
                _registerFlow.value = Idle
        }
    }

    private fun registerUserInFirebase(name: String, email: String, password: String) = viewModelScope.launch {
        _registerFlow.value = Resource.Loading
        val result = firebaseAuthRepository.registerUserInFirebase(name, email, password)
        _registerFlow.value = result
    }
}