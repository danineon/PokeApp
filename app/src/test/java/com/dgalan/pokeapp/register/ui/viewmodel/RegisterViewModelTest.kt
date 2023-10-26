package com.dgalan.pokeapp.register.ui.viewmodel

import com.dgalan.pokeapp.firebasedata.repository.FirebaseAuthRepository
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.ConfirmPasswordChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.ConfirmPasswordVisibilityChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.EmailChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.NameChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.PasswordChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.PasswordVisibilityChanged
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.RegisterButtonClicked
import com.dgalan.pokeapp.register.ui.state.RegisterUIEvent.ResetResourceState
import com.dgalan.pokeapp.utils.state.Resource
import com.dgalan.pokeapp.utils.state.Resource.Idle
import com.google.firebase.auth.FirebaseUser
import io.mockk.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.*
import org.junit.Assert.*

private lateinit var viewModel: RegisterViewModel
private lateinit var firebaseAuthRepository: FirebaseAuthRepository
private lateinit var ioDispatcher: CoroutineDispatcher

private const val EMAIL_STRING = "email"
private const val PASSWORD_STRING = "password"
private const val NAME_STRING = "name"
private const val EMPTY_STRING = ""

class RegisterViewModelTest {

    @Before
    fun setUp() {
        firebaseAuthRepository = mockk(relaxed = true)
        ioDispatcher = Dispatchers.IO
        viewModel = spyk(RegisterViewModel(firebaseAuthRepository, ioDispatcher), recordPrivateCalls = true)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test onEvent() function with NameChanged passed as a event parameter`() {
        val event = mockk<NameChanged>(relaxed = true)

        every { event.name } returns NAME_STRING

        viewModel.onEvent(event)

        assertEquals(event.name, viewModel.registerUIState.value.name)
    }

    @Test
    fun `test onEvent() function with EmailChanged passed as a event parameter with valid email`() {
        val event = mockk<EmailChanged>(relaxed = true)

        every { event.email } returns EMAIL_STRING
        every { viewModel["isLoginButtonClicked1stTime"]() } returns true
        every { viewModel["isValidEmail"]() } returns true

        viewModel.onEvent(event)

        verify {
            viewModel["isLoginButtonClicked1stTime"]()
            viewModel["validateEmail"]()
            viewModel["isValidEmail"]()
        }
        assertEquals(event.email, viewModel.registerUIState.value.email)
    }

    @Test
    fun `test onEvent() function with EmailChanged passed as a event parameter with invalid email`() {
        val event = mockk<EmailChanged>(relaxed = true)

        every { event.email } returns EMAIL_STRING
        every { viewModel["isLoginButtonClicked1stTime"]() } returns true
        every { viewModel["isValidEmail"]() } returns false

        viewModel.onEvent(event)

        verify {
            viewModel["isLoginButtonClicked1stTime"]()
            viewModel["validateEmail"]()
            viewModel["isValidEmail"]()
        }
        assertEquals(event.email, viewModel.registerUIState.value.email)
    }

    @Test
    fun `test onEvent() function with PasswordChanged passed as a event parameter with valid password and matches passwords`() {
        val event = mockk<PasswordChanged>(relaxed = true)

        every { event.password } returns PASSWORD_STRING
        every { viewModel["isLoginButtonClicked1stTime"]() } returns true
        every { viewModel["isValidPassword"]() } returns true
        every { viewModel["isValidMatchesPassword"]() } returns true

        viewModel.onEvent(event)

        verify {
            viewModel["isLoginButtonClicked1stTime"]()
            viewModel["validatePassword"]()
            viewModel["validateMatchesPasswords"]()
            viewModel["isValidPassword"]()
            viewModel["isValidMatchesPassword"]()
        }
        assertEquals(event.password, viewModel.registerUIState.value.password)
    }

    @Test
    fun `test onEvent() function with PasswordChanged passed as a event parameter with invalid password and don't matches passwords`() {
        val event = mockk<PasswordChanged>(relaxed = true)

        every { event.password } returns PASSWORD_STRING
        every { viewModel["isLoginButtonClicked1stTime"]() } returns true
        every { viewModel["isValidPassword"]() } returns false
        every { viewModel["isValidMatchesPassword"]() } returns false

        viewModel.onEvent(event)

        verify {
            viewModel["isLoginButtonClicked1stTime"]()
            viewModel["validatePassword"]()
            viewModel["validateMatchesPasswords"]()
            viewModel["isValidPassword"]()
            viewModel["isValidMatchesPassword"]()
        }
        assertEquals(event.password, viewModel.registerUIState.value.password)
    }

    @Test
    fun `test onEvent() function with PasswordVisibilityChanged passed as a event parameter`() {
        val event = mockk<PasswordVisibilityChanged>(relaxed = true)

        every { event.isPasswordVisible } returns true

        viewModel.onEvent(event)

        assertEquals(event.isPasswordVisible, viewModel.registerUIState.value.isPasswordVisible)
    }

    @Test
    fun `test onEvent() function with ConfirmPasswordChanged passed as a event parameter`() {
        val event = mockk<ConfirmPasswordChanged>(relaxed = true)

        every { event.confirmPassword } returns PASSWORD_STRING
        every { viewModel["isLoginButtonClicked1stTime"]() } returns true
        every { viewModel["isValidMatchesPassword"]() } returns true

        viewModel.onEvent(event)

        verify {
            viewModel["isLoginButtonClicked1stTime"]()
            viewModel["validateMatchesPasswords"]()
            viewModel["isValidMatchesPassword"]()
        }
        assertEquals(event.confirmPassword, viewModel.registerUIState.value.confirmPassword)
    }

    @Test
    fun `test onEvent() function with ConfirmPasswordVisibilityChanged passed as a event parameter`() {
        val event = mockk<ConfirmPasswordVisibilityChanged>(relaxed = true)

        every { event.isConfirmPasswordVisible } returns true

        viewModel.onEvent(event)

        assertEquals(event.isConfirmPasswordVisible, viewModel.registerUIState.value.isConfirmPasswordVisible)
    }

    @Test
    fun `test onEvent() function with ResetResourceState passed as a event parameter`() {
        val event = mockk<ResetResourceState>(relaxed = true)

        viewModel.onEvent(event)

        assertEquals(Idle, viewModel.registerFlow.value)
    }

    @Test
    fun `test onEvent() function with RegisterButtonClicked passed as a event parameter success with valid parameters`() {
        val event = mockk<RegisterButtonClicked>(relaxed = true)
        val result = Resource.Success(mockk<FirebaseUser>(relaxed = true))

        every { viewModel["isValidEmail"]() } returns true
        every { viewModel["isValidPassword"]() } returns true
        every { viewModel["isValidMatchesPassword"]() } returns true
        coEvery { viewModel["registerUserInFirebase"](EMAIL_STRING, PASSWORD_STRING, NAME_STRING) } returns result

        viewModel.onEvent(event)

        coVerify { viewModel["registerUserInFirebase"](EMPTY_STRING, EMPTY_STRING, EMPTY_STRING) }

        verify {
            viewModel["isValidEmail"]()
            viewModel["isValidPassword"]()
            viewModel["isValidMatchesPassword"]()
            viewModel["tryRegisterUser"]()
        }
        assertTrue(viewModel.registerUIState.value.isLoginButtonClicked1stTime)
    }

    @Test
    fun `test onEvent() function with RegisterButtonClicked passed as a event parameter with invalid parameters`() {
        val event = mockk<RegisterButtonClicked>(relaxed = true)

        every { viewModel["isLoginButtonClicked1stTime"]() } returns false
        every { viewModel["isValidEmail"]() } returns false
        every { viewModel["isValidPassword"]() } returns false
        every { viewModel["isValidMatchesPassword"]() } returns false

        viewModel.onEvent(event)

        verify {
            viewModel["showErrorsInTextFields"]()
            viewModel["isValidEmail"]()
            viewModel["isValidPassword"]()
            viewModel["isValidMatchesPassword"]()
            viewModel["tryRegisterUser"]()
        }
        assertTrue(viewModel.registerUIState.value.isLoginButtonClicked1stTime)
        assertFalse(viewModel.registerUIState.value.isValidEmail)
        assertFalse(viewModel.registerUIState.value.isValidPassword)
        assertFalse(viewModel.registerUIState.value.isValidMatchesPassword)
    }

    @Test
    fun `test onEvent() function with RegisterButtonClicked passed as a event parameter error with valid parameters`() {
        val event = mockk<RegisterButtonClicked>(relaxed = true)
        val result = Resource.Error(mockk(relaxed = true))

        every { viewModel["isValidEmail"]() } returns true
        every { viewModel["isValidPassword"]() } returns true
        every { viewModel["isValidMatchesPassword"]() } returns true
        coEvery { viewModel["registerUserInFirebase"](EMAIL_STRING, PASSWORD_STRING, NAME_STRING) } returns result

        viewModel.onEvent(event)

        coVerify { viewModel["registerUserInFirebase"](EMPTY_STRING, EMPTY_STRING, EMPTY_STRING) }

        verify {
            viewModel["isValidEmail"]()
            viewModel["isValidPassword"]()
            viewModel["isValidMatchesPassword"]()
            viewModel["tryRegisterUser"]()
        }
        assertTrue(viewModel.registerUIState.value.isLoginButtonClicked1stTime)
    }
}