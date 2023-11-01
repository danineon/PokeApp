package com.dgalan.pokeapp.authetication.data.di

import com.dgalan.pokeapp.authetication.data.repository.FirebaseAuthRepository
import com.dgalan.pokeapp.authetication.data.repository.FirebaseAuthRepositoryContract
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class FirebaseAuthModule {

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuthRepository(firebaseAuthRepository: FirebaseAuthRepository): FirebaseAuthRepositoryContract =
        firebaseAuthRepository
}