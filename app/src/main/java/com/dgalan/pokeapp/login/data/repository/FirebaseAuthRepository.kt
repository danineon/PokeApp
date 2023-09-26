package com.dgalan.pokeapp.login.data.repository

import com.dgalan.pokeapp.login.data.FirebaseAuthRepositoryContract
import com.dgalan.pokeapp.utils.state.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    FirebaseAuthRepositoryContract {

    override suspend fun loginUserInFirebase(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}