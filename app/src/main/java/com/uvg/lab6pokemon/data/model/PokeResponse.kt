package com.uvg.lab6pokemon.data.model

data class PokeResponse(val results: List<Pokemon>) {

    // Firebase needs an empty constructor to serialize the info
    // DO-NOT-REMOVE
    @Suppress("unused")
    constructor() : this(emptyList())
}

// Modelo para el Pokémon (simplificado)
data class Pokemon(
    val name: String,
    val url: String
) {
    // Extraer el ID del Pokémon desde la URL
    val id: Int
        get() = url.dropLast(1).takeLastWhile { it.isDigit() }.toInt()

    val imageUrlFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    val imageUrlBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png"
    val imageUrlShinyFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png"
    val imageUrlShinyBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/$id.png"

    // Firebase needs an empty constructor to serialize the info
    // DO-NOT-REMOVE
    @Suppress("unused")
    constructor() : this("", "")
}
