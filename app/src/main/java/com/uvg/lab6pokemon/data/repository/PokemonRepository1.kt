package com.uvg.lab6pokemon.data.repository

import com.uvg.lab6pokemon.data.model.Pokemon
import com.uvg.lab6pokemon.data.remote.PokeApiService

class PokemonRepository1(private val apiService: PokeApiService): PokemonRepository {

    override suspend fun getPokemonList(limit: Int): List<Pokemon> {
        return apiService.getPokemonList(limit).results
    }
}
