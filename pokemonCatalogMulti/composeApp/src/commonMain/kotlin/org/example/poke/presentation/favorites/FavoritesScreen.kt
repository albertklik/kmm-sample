package org.example.poke.presentation.favorites

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getScreenModel
import org.example.poke.presentation.components.PokemonRow

object FavoritesScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<FavoritesScreenModel>()
        val state by screenModel.state.collectAsState()

        // Carrega os favoritos sempre que a tela se torna visível
        LaunchedEffect(Unit) {
            screenModel.onIntent(FavoritesIntent.LoadFavorites)
        }

        FavoritesContent(
            state = state,
            onIntent = screenModel::onIntent
        )
    }
}



@Composable
fun FavoritesContent(
    state: FavoritesState,
    onIntent: (FavoritesIntent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.pokemons.isEmpty()) {
            Text(
                text = "Você ainda não tem Pokémon favoritos.",
                modifier = Modifier.align(Alignment.Center).padding(16.dp),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.pokemons, key = { it.id }) { pokemon ->
                    PokemonRow(
                        pokemon = pokemon,
                        onFavoriteClick = { onIntent(FavoritesIntent.RemoveFavorite(pokemon.id)) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

