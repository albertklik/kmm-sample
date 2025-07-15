package org.example.poke.presentation.list

data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val error: String? = null,
    val page: Int = 0
)

// INTENT: Ações do usuário
sealed interface PokemonListIntent {
    data object LoadNextPage : PokemonListIntent
    data class ToggleFavorite(val pokemon: Pokemon) : PokemonListIntent
}