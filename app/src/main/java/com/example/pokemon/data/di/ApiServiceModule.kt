package com.example.pokemon.data.di

import com.example.pokemon.data.network.ApiClient
import com.example.pokemon.data.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = ApiClient.apiService
}