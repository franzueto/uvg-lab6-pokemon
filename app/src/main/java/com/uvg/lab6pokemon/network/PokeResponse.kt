package com.uvg.lab6pokemon.network

data class PokeResponse(val results: List<Pokemon>) {
    // Firebase requires an empty constructor to deserialize objects
    @Suppress("unused")
    constructor() : this(emptyList())
}

// Modelo para el Pokémon (simplificado)
data class Pokemon(
    val name: String,
    val id: String
) {
    // Firebase requires an empty constructor to deserialize objects
    @Suppress("unused")
    constructor() : this("", "")

    val imageUrlFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    val imageUrlBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/$id.png"
    val imageUrlShinyFront: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/$id.png"
    val imageUrlShinyBack: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/$id.png"
}
