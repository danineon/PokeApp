package com.dgalan.pokeapp.login.ui.viewmodel

import com.dgalan.pokeapp.firebasedata.repository.FirebaseAuthRepository
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.EmailChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.LoginButtonClicked
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.PasswordChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.PasswordVisibilityChanged
import com.dgalan.pokeapp.login.ui.state.LoginUIEvent.ResetResourceState
import com.dgalan.pokeapp.utils.state.Resource
import com.dgalan.pokeapp.utils.state.Resource.Idle
import com.google.firebase.auth.FirebaseUser
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.Assert.*

private lateinit var viewModel: LoginViewModel
private lateinit var firebaseAuthRepository: FirebaseAuthRepository
private lateinit var ioDispatcher: CoroutineDispatcher

private const val EMAIL_STRING = "email"
private const val PASSWORD_STRING = "password"
private const val EMPTY_STRING = ""
private const val IS_PASSWORD_VISIBLE_STRING = true

class LoginViewModelTest {

    @Before
    fun setUp() {
        firebaseAuthRepository = mockk(relaxed = true)
        ioDispatcher = Dispatchers.IO
        viewModel = spyk(LoginViewModel(firebaseAuthRepository, ioDispatcher), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test onEvent() function with EmailChanged passed as a event parameter`() {
        val event = mockk<EmailChanged>(relaxed = true)

        every { event.email } returns EMAIL_STRING

        viewModel.onEvent(event)

        assertEquals(event.email, viewModel.loginUIState.value.email)
    }

    @Test
    fun `test onEvent() function with PasswordChanged passed as a event parameter`() {
        val event = mockk<PasswordChanged>(relaxed = true)

        every { event.password } returns PASSWORD_STRING

        viewModel.onEvent(event)

        assertEquals(event.password, viewModel.loginUIState.value.password)
    }

    @Test
    fun `test onEvent() function with PasswordVisibilityChanged passed as a event parameter`() {
        val event = mockk<PasswordVisibilityChanged>()

        every { event.isPasswordVisible } returns IS_PASSWORD_VISIBLE_STRING

        viewModel.onEvent(event)

        assertEquals(event.isPasswordVisible, viewModel.loginUIState.value.isPasswordVisible)
    }

    @Test
    fun `test onEvent() function with LoginButtonClicked passed as a event parameter with Success result`() {
        val event = mockk<LoginButtonClicked>(relaxed = true)
        val result = Resource.Success(mockk<FirebaseUser>(relaxed = true))

        every { viewModel.loginFlow.value } returns result

        coEvery { viewModel["loginUserInFirebase"](EMAIL_STRING, PASSWORD_STRING) } returns result

        viewModel.onEvent(event)

        coVerify { viewModel["loginUserInFirebase"](EMPTY_STRING, EMPTY_STRING) }
    }

    @Test
    fun `test onEvent() function with LoginButtonClicked passed as a event parameter with Error result`() {
        val event = mockk<LoginButtonClicked>(relaxed = true)
        val result = Resource.Error(mockk(relaxed = true), "error message")
        every { viewModel.loginFlow.value } returns result

        coEvery { viewModel["loginUserInFirebase"](EMAIL_STRING, PASSWORD_STRING) } returns result

        viewModel.onEvent(event)

        coVerify { viewModel["loginUserInFirebase"](EMPTY_STRING, EMPTY_STRING) }
    }

    @Test
    fun `test onEvent() function with ResetResourceState passed as a event parameter`() {
        val event = mockk<ResetResourceState>()

        viewModel.onEvent(event)

        assertEquals(Idle, viewModel.loginFlow.value)
    }
}


