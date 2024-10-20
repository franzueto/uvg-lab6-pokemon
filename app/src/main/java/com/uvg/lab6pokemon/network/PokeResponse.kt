package com.uvg.lab6pokemon.network

data class PokeResponse(val results: List<Pokemon>)

// Modelo para el Pokémon (simplificado)
data class Pokemon(
    val name: String,
    val url: String
) {
    // Extraer el ID del Pokémon desde la URL
    val id: Int
        get() = url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()

    // Construir la URL de la imagen
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}
