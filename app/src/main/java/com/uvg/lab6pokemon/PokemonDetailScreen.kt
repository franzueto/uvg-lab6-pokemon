package com.uvg.lab6pokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.uvg.lab6pokemon.network.Pokemon
import java.util.Locale

@Composable
fun PokemonDetailScreen(name: String, id: Int) {
    val pokemon = remember { Pokemon(name, "https://pokeapi.co/api/v2/pokemon/$id/") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // Imagenes del Pokémon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrlFront),
                contentDescription = "Front",
                modifier = Modifier.size(96.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrlBack),
                contentDescription = "Back",
                modifier = Modifier.size(96.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrlShinyFront),
                contentDescription = "Shiny Front",
                modifier = Modifier.size(96.dp)
            )
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrlShinyBack),
                contentDescription = "Shiny Back",
                modifier = Modifier.size(96.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailPreview() {
    PokemonDetailScreen(name = "Pikachu", id = 25)
}