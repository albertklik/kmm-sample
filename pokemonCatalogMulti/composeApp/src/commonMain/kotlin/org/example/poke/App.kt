package org.example.poke

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.example.poke.di.appModule
import org.example.poke.presentation.favorites.FavoritesScreen
import org.example.poke.presentation.list.PokemonListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.dsl.koinConfiguration
import org.koin.core.annotation.KoinExperimentalAPI


object PokemonListTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.List)
            val title = "Lista"
            return remember { TabOptions(index = 0u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        // O conteúdo desta aba é a tela da lista de Pokémon
        PokemonListScreen.Content()
    }
}

// Objeto para representar a aba de Favoritos
object FavoritesTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Favorite)
            val title = "Favoritos"
            return remember { TabOptions(index = 1u, title = title, icon = icon) }
        }

    @Composable
    override fun Content() {
        // O conteúdo desta aba é a tela de favoritos
        FavoritesScreen.Content()
    }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    val tabs = listOf(PokemonListTab, FavoritesTab)
    MaterialTheme {
        KoinMultiplatformApplication(
            koinConfiguration {
                modules(appModule())
            }
        ) {
            // O BottomSheetNavigator envolve tudo para permitir mostrar modais
            BottomSheetNavigator {
                // O TabNavigator gere a navegação principal entre as abas
                TabNavigator(tab = PokemonListTab) { tabNavigator ->
                    Scaffold(
                        content = { padding ->
                            // O conteúdo principal da tela é a aba atualmente selecionada
                            Box(Modifier.padding(padding)) {
                                CurrentTab()
                            }
                        },
                        bottomBar = {
                            // A barra de navegação inferior
                            NavigationBar {
                                tabs.forEach { tab ->
                                    NavigationBarItem(
                                        selected = tabNavigator.current == tab,
                                        onClick = { tabNavigator.current = tab },
                                        icon = {
                                            Icon(
                                                painter = tab.options.icon!!,
                                                contentDescription = tab.options.title
                                            )
                                        },
                                        label = { Text(tab.options.title) }
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
