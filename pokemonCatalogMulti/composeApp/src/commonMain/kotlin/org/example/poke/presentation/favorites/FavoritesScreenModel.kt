package org.example.poke.presentation.favorites

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.example.poke.domain.repository.PokemonRepository

class FavoritesScreenModel(
    private val repository: PokemonRepository
) : StateScreenModel<FavoritesState>(FavoritesState()) {

    fun onIntent(intent: FavoritesIntent) {
        when (intent) {
            is FavoritesIntent.LoadFavorites -> loadFavorites()
            is FavoritesIntent.RemoveFavorite -> removeFavorite(intent.pokemonId)
        }
    }

    private fun loadFavorites() {
        screenModelScope.launch {
            mutableState.value = state.value.copy(isLoading = true)
            val favorites = repository.getFavoritePokemons()
            mutableState.value = state.value.copy(isLoading = false, pokemons = favorites)
        }
    }

    private fun removeFavorite(pokemonId: Int) {
        screenModelScope.launch {
            repository.removeFavorite(pokemonId)
            loadFavorites() // Recarrega a lista após a remoção
        }
    }
}