package org.example.poke.presentation.list

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.example.poke.domain.repository.PokemonRepository

class PokemonListScreenModel(
    private val repository: PokemonRepository
) : StateScreenModel<PokemonListState>(PokemonListState()) {

    init {
        onIntent(PokemonListIntent.LoadNextPage)
    }

    fun onIntent(intent: PokemonListIntent) {
        when (intent) {
            is PokemonListIntent.LoadNextPage -> loadPokemons()
            is PokemonListIntent.ToggleFavorite -> toggleFavorite(intent.pokemon)
        }
    }

    private fun loadPokemons() {
        // Lógica para buscar pokémons do repositório,
        // atualizar o state com loading, sucesso ou erro.
        // screenModelScope.launch { ... }
    }

    private fun toggleFavorite(pokemon: Pokemon) {
        // Lógica para favoritar/desfavoritar usando o repositório.
        // screenModelScope.launch { ... }
    }
}