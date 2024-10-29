package com.uvg.lab6pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvg.lab6pokemon.ui.screen.PokemonDetailScreen
import com.uvg.lab6pokemon.ui.screen.PokemonListScreen
import com.uvg.lab6pokemon.ui.theme.Lab6PokemonTheme

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
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            if (name != null && id != null) {
                PokemonDetailScreen(name, id)
            }
        }
    }
}
