package com.example.pokemon.di

import com.example.pokemon.network.ApiService
import com.example.pokemon.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        apiService: ApiService
    ): PokemonRepository = PokemonRepository(apiService)
}