package com.uvg.lab6pokemon.data.remote

import com.uvg.lab6pokemon.data.model.PokeResponse
import retrofit2.http.GET
import retrofit2.http.Query

// Definir la interfaz de PokeAPI
interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int): PokeResponse
}
