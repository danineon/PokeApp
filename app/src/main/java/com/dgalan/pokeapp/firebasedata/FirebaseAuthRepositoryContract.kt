package com.dgalan.pokeapp.firebasedata

import com.dgalan.pokeapp.utils.state.Resource
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepositoryContract {

    suspend fun loginUserInFirebase(email: String, password: String): Resource<FirebaseUser>
    suspend fun registerUserInFirebase(name: String, email: String, password: String): Resource<FirebaseUser>
}