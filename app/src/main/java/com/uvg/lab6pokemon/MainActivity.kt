package com.uvg.lab6pokemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.uvg.lab6pokemon.network.PokeResponse
import com.uvg.lab6pokemon.network.Pokemon
import com.uvg.lab6pokemon.ui.theme.Lab6PokemonTheme
import kotlinx.coroutines.launch
import java.util.Locale

private const val TAG = "pokemon-app"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab6PokemonTheme {
                PokemonApp()
            }
        }
    }
}

@Composable
fun PokemonApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "pokemon_list") {
        composable("pokemon_list") {
            PokemonListScreen(navController)
        }
        composable("pokemon_detail/{name}/{id}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val id = backStackEntry.arguments?.getString("id")
            if (name != null && id != null) {
                PokemonDetailScreen(name, id)
            }
        }
    }
}

@Composable
fun PokemonListScreen(navController: NavHostController) {
    val pokemonList = remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val db = Firebase.firestore
            db.collection("pokemon").document("all")
                .get()
                .addOnSuccessListener { document ->
                    Log.d(TAG, "${document.id} => ${document.data}")

                    val results = document.toObject(PokeResponse::class.java)
                    pokemonList.value = results?.results ?: emptyList()
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents.", exception)
                }
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
                PokemonCard(pokemon, navController)
            }
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Navegación a la pantalla de detalles
                navController.navigate("pokemon_detail/${pokemon.name}/${pokemon.id}")
            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Imagen del Pokémon
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrlFront),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )

            // Nombre del Pokémon
            Text(
                text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonListScreenPreview() {
    val samplePokemonList = listOf(
        Pokemon(name = "pikachu", id = "25"),
        Pokemon(name = "bulbasaur", id = "1")
    )

    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(samplePokemonList) { pokemon ->
            PokemonCard(pokemon, rememberNavController())
        }
    }
}