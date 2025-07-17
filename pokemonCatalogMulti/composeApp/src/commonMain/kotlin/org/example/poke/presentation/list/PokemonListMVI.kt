package org.example.poke.presentation.list

import org.example.poke.domain.model.Pokemon

/**
 * Representa o estado visual da tela de lista de Pokémon.
 * É uma classe de dados imutável, então cada mudança cria um novo estado.
 *
 * @param isLoading Indica se uma operação de carregamento (inicial ou de página) está em andamento.
 * @param pokemons A lista de Pokémon atualmente carregada para ser exibida.
 * @param error Uma mensagem de erro, caso ocorra uma falha na rede. Nulo se não houver erro.
 * @param page O número da página atual para a paginação.
 * @param endReached Indica se todas as páginas de Pokémon foram carregadas. Evita chamadas de rede desnecessárias.
 */
data class PokemonListState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val error: String? = null,
    val page: Int = 0,
    val endReached: Boolean = false
)

/**
 * Representa as ações que o utilizador pode iniciar na UI.
 * O ScreenModel observará esses intents e reagirá a eles.
 */
sealed interface PokemonListIntent {
    /**
     * Intento para carregar a próxima página de Pokémon.
     */
    data object LoadNextPage : PokemonListIntent

    /**
     * Intento para atualizar a lista do zero (ex: pull-to-refresh).
     */
    data object Refresh : PokemonListIntent

    /**
     * Intento para adicionar ou remover um Pokémon da lista de favoritos.
     * @param pokemon O Pokémon que será afetado.
     */
    data class ToggleFavorite(val pokemon: Pokemon) : PokemonListIntent
}