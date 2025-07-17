package org.example.poke.presentation.favorites

import org.example.poke.domain.model.Pokemon

// STATE
data class FavoritesState(
    val pokemons: List<Pokemon> = emptyList(),
    val isLoading: Boolean = true
)

// INTENT
sealed interface FavoritesIntent {
    data object LoadFavorites : FavoritesIntent
    data class RemoveFavorite(val pokemonId: Int) : FavoritesIntent
}