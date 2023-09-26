package com.dgalan.pokeapp.login.data

import com.dgalan.pokeapp.utils.state.Resource
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthRepositoryContract {

    suspend fun loginUserInFirebase(email: String, password: String): Resource<FirebaseUser>
}