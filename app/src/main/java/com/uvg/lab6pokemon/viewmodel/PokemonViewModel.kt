package com.uvg.lab6pokemon.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.uvg.lab6pokemon.data.model.Pokemon
import com.uvg.lab6pokemon.data.remote.RetrofitClient
import com.uvg.lab6pokemon.data.repository.PokemonRepository
import com.uvg.lab6pokemon.data.repository.PokemonRepository1
import com.uvg.lab6pokemon.data.repository.PokemonRepository2
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {
    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    fun fetchPokemonList(limit: Int) {
        viewModelScope.launch {
            val list = repository.getPokemonList(limit)
            _pokemonList.postValue(list)
        }
    }

    companion object {

        val RetrofitFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonViewModel(
                    PokemonRepository1(RetrofitClient.apiService)
                ) as T
            }
        }

        val FirebaseFactory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PokemonViewModel(
                    PokemonRepository2(Firebase.firestore)
                ) as T
            }
        }
    }
}
