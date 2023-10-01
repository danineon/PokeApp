package com.dgalan.pokeapp.firebasedata.repository

import com.dgalan.pokeapp.firebasedata.FirebaseAuthRepositoryContract
import com.dgalan.pokeapp.utils.state.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val ERROR_LOGIN = "An internal error has occurred. [ INVALID_LOGIN_CREDENTIALS ]"

class FirebaseAuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    FirebaseAuthRepositoryContract {

    override suspend fun loginUserInFirebase(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            val errorMessage: String = when (e.message) {
                ERROR_LOGIN -> "Invalid email or password"
                else -> "Unknown error"
            }
            Resource.Error(e, errorMessage)
        }
    }

    override suspend fun registerUserInFirebase(
        name: String,
        email: String,
        password: String
    ): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}