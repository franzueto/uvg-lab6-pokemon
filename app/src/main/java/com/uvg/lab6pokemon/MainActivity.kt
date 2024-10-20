package com.uvg.lab6pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.uvg.lab6pokemon.network.Pokemon
import com.uvg.lab6pokemon.network.RetrofitClient
import com.uvg.lab6pokemon.ui.theme.Lab6PokemonTheme
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab6PokemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    PokemonListScreen(Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun PokemonListScreen(modifier: Modifier) {
    val pokemonList = remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val response = RetrofitClient.apiService.getPokemonList(100)  // Puedes cambiar el límite aquí
            pokemonList.value = response.results
        }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pokémon List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Usar LazyColumn para listas largas
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),  // Padding en la parte superior e inferior
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre cada item
        ) {
            items(pokemonList.value) { pokemon ->
                PokemonCard(pokemon)
            }
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        //elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
        //backgroundColor = Color.LightGray
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Imagen del Pokémon
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrl),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Nombre del Pokémon
            Text(
                text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.alignByBaseline()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListScreenPreview() {
    val samplePokemonList = listOf(
        Pokemon(name = "pikachu", url = "https://pokeapi.co/api/v2/pokemon/25/"),
        Pokemon(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/")
    )

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(samplePokemonList) { pokemon ->
            PokemonCard(pokemon)
        }
    }
}