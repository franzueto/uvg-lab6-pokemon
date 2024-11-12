package com.uvg.lab6pokemon.di

import com.uvg.lab6pokemon.data.remote.PokeApiService
import com.uvg.lab6pokemon.data.remote.RetrofitClient
import com.uvg.lab6pokemon.data.repository.PokemonRepository
import com.uvg.lab6pokemon.data.repository.PokemonRepository1
import com.uvg.lab6pokemon.viewmodel.PokemonViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    viewModel { PokemonViewModel(get()) }

    single { PokemonRepository1(get()) } bind PokemonRepository::class

    single { RetrofitClient.apiService } bind PokeApiService::class
}
