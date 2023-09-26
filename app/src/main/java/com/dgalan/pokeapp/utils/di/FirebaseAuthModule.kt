package com.dgalan.pokeapp.utils.di

import com.dgalan.pokeapp.firebasedata.FirebaseAuthRepositoryContract
import com.dgalan.pokeapp.firebasedata.repository.FirebaseAuthRepository
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