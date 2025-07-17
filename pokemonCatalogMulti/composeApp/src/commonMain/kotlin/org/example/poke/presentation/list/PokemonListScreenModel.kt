package org.example.poke.presentation.list

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.example.poke.domain.model.Pokemon
import org.example.poke.domain.repository.PokemonRepository

/**
 * O ScreenModel (semelhante a um ViewModel) para a tela de lista de Pokémon.
 * Ele gerencia o estado da UI (PokemonListState) e a lógica de negócios.
 *
 * @param repository O repositório para buscar dados de Pokémon.
 */
class PokemonListScreenModel(
    private val repository: PokemonRepository
) : StateScreenModel<PokemonListState>(PokemonListState()) {

    init {
        // Carrega a primeira página de Pokémon assim que o ScreenModel é criado.
        loadPokemons()
    }

    /**
     * Ponto de entrada para todos os eventos da UI.
     * @param intent O evento que o utilizador iniciou.
     */
    fun onIntent(intent: PokemonListIntent) {
        when (intent) {
            is PokemonListIntent.LoadNextPage -> loadPokemons()
            is PokemonListIntent.ToggleFavorite -> toggleFavorite(intent.pokemon)
            is PokemonListIntent.Refresh -> refreshList()
        }
    }

    /**
     * Reseta o estado e carrega a lista do início.
     */
    private fun refreshList() {
        // Reseta o estado para os valores iniciais e chama o carregamento.
        mutableState.value = PokemonListState()
        loadPokemons()
    }

    /**
     * Carrega a próxima página de Pokémon do repositório.
     */
    private fun loadPokemons() {
        // Evita chamadas múltiplas se já estiver a carregar ou se o fim da lista foi alcançado.
        if (state.value.isLoading || state.value.endReached) return

        screenModelScope.launch {
            // Emite um novo estado para mostrar o indicador de carregamento.
            mutableState.value = state.value.copy(isLoading = true, error = null)

            // Busca os dados do repositório.
            repository.getPokemonList(state.value.page)
                .onSuccess { newPokemons ->
                    // Em caso de sucesso, atualiza o estado com a nova lista de Pokémon.
                    mutableState.value = state.value.copy(
                        isLoading = false,
                        pokemons = state.value.pokemons + newPokemons,
                        page = state.value.page + 1,
                        endReached = newPokemons.isEmpty() // Se a API não retornar nada, chegamos ao fim.
                    )
                }
                .onFailure { error ->
                    // Em caso de falha, atualiza o estado com a mensagem de erro.
                    mutableState.value = state.value.copy(
                        isLoading = false,
                        error = error.message ?: "Ocorreu um erro desconhecido"
                    )
                }
        }
    }

    /**
     * Adiciona ou remove um Pokémon dos favoritos.
     * @param pokemon O Pokémon a ser modificado.
     */
    private fun toggleFavorite(pokemon: Pokemon) {
        screenModelScope.launch {
            if (pokemon.isFavorite) {
                repository.removeFavorite(pokemon.id)
            } else {
                repository.addFavorite(pokemon)
            }

            // Para uma melhor experiência do utilizador, atualizamos o estado do item na lista
            // localmente e de forma imediata, sem precisar de recarregar tudo da rede.
            val updatedPokemons = state.value.pokemons.map {
                if (it.id == pokemon.id) {
                    it.copy(isFavorite = !pokemon.isFavorite)
                } else {
                    it
                }
            }
            mutableState.value = state.value.copy(pokemons = updatedPokemons)
        }
    }
}
