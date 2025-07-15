package org.example.poke.presentation.list

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel

object PokemonListScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel = getScreenModel<PokemonListScreenModel>()
        val state = screenModel.state.value

        // Aqui vai o Composable que renderiza a UI
        // com base no 'state' (ex: Loading, Success, Error)
        PokemonListContent(
            state = state,
            onIntent = screenModel::onIntent
        )
    }
}