package org.example.poke.presentation.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.poke.presentation.components.PokemonRow

@Composable
fun PokemonListContent(
    state: PokemonListState,
    onIntent: (PokemonListIntent) -> Unit
) {
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        // Se a lista está vazia e a carregar pela primeira vez, mostra um indicador central
        if (state.pokemons.isEmpty() && state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        // Se houver um erro e a lista estiver vazia, mostra a mensagem de erro
        else if (state.pokemons.isEmpty() && state.error != null) {
            Text(
                text = "Falha ao carregar Pokémon.\n${state.error}",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center).padding(16.dp)
            )
        }
        // Caso contrário, mostra a lista de Pokémon
        else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp) // Espaçamento entre os itens
            ) {
                items(state.pokemons, key = { it.id }) { pokemon ->
                    PokemonRow(
                        pokemon = pokemon,
                        onFavoriteClick = { onIntent(PokemonListIntent.ToggleFavorite(pokemon)) }
                    )
                }

                // Mostra um indicador de carregamento no final da lista ao carregar a próxima página
                if (state.isLoading && state.pokemons.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    // Lógica para paginação infinita (carregar mais itens)
    // `LaunchedEffect` observa as mudanças no estado da lista
    val isScrolledToEnd by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.lastOrNull()
                // Verifica se o último item visível é o último item da lista
                lastVisibleItem?.index == layoutInfo.totalItemsCount - 1
            }
        }
    }

    // Quando o utilizador chega ao fim da lista e não estamos a carregar, envia o intento para carregar a próxima página
    if (isScrolledToEnd && !state.isLoading && !state.endReached) {
        LaunchedEffect(Unit) {
            onIntent(PokemonListIntent.LoadNextPage)
        }
    }
}