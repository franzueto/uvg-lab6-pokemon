package com.uvg.lab6pokemon.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.uvg.lab6pokemon.data.model.Pokemon
import com.uvg.lab6pokemon.ui.component.PokemonCard
import com.uvg.lab6pokemon.viewmodel.PokemonViewModel

@Composable
fun PokemonListScreen(
    navController: NavHostController,
    viewModel: PokemonViewModel = viewModel( factory = PokemonViewModel.RetrofitFactory )
) {
    val pokemonList by viewModel.pokemonList.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonList(100)
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pokémon List", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        // Usar LazyColumn para listas largas
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),  // Padding en la parte superior e inferior
            verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre cada item
        ) {
            items(pokemonList) { pokemon ->
                PokemonCard(pokemon, navController)
            }
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
            PokemonCard(pokemon, rememberNavController())
        }
    }
}
