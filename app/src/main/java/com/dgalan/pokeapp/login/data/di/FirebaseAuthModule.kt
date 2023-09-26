package com.dgalan.pokeapp.login.data.di

import com.dgalan.pokeapp.login.data.FirebaseAuthRepositoryContract
import com.dgalan.pokeapp.login.data.repository.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class FirebaseAuthModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseAuthRepository(firebaseAuthRepository: FirebaseAuthRepository): FirebaseAuthRepositoryContract =
        firebaseAuthRepository
}