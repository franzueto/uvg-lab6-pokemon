package com.uvg.lab6pokemon.data.repository

import com.uvg.lab6pokemon.data.model.Pokemon

interface PokemonRepository {

    suspend fun getPokemonList(limit: Int): List<Pokemon>
}
