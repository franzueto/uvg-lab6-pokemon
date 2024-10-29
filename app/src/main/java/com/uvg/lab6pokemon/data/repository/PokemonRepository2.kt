package com.uvg.lab6pokemon.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.uvg.lab6pokemon.data.model.PokeResponse
import com.uvg.lab6pokemon.data.model.Pokemon
import kotlinx.coroutines.tasks.await

class PokemonRepository2(private val db: FirebaseFirestore): PokemonRepository {

    override suspend fun getPokemonList(limit: Int): List<Pokemon> {
        return db.collection("pokemon").document("all")
            .get()
            .await()
            .toObject(PokeResponse::class.java)
            ?.results ?: emptyList()
    }
}
