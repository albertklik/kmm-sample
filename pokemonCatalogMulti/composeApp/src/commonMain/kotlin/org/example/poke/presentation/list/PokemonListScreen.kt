package org.example.poke.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel

object PokemonListScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<PokemonListScreenModel>()
        val state by screenModel.state.collectAsState()

        // Aqui vai o Composable que renderiza a UI
        // com base no 'state' (ex: Loading, Success, Error)
        PokemonListContent(
            state = state,
            onIntent = screenModel::onIntent
        )
    }
}