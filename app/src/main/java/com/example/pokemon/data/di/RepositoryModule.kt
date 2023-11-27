package com.example.pokemon.data.di

import com.example.pokemon.data.network.ApiService
import com.example.pokemon.data.repository.PokemonRepository
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