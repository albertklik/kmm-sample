package org.example.poke

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import cafe.adriel.voyager.navigator.drawer.DrawerNavigator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.poke.presentation.list.PokemonListScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import pokemoncatalogmulti.composeapp.generated.resources.Res
import pokemoncatalogmulti.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    MaterialTheme {
        DrawerNavigator(
            drawerContent = {
                // Conteúdo do Drawer (Menu Lateral)
                PermanentDrawerSheet {
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "List") },
                        label = { Text("Pokémon List") },
                        selected = it.lastItem is PokemonListScreen,
                        onClick = { it.replaceAll(PokemonListScreen) }
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                        label = { Text("Favorites") },
                        selected = it.lastItem is FavoritesScreen,
                        onClick = { it.replaceAll(FavoritesScreen) }
                    )
                }
            }
        ) { drawerNavigator ->
            // O conteúdo principal da tela
            Navigator(screen = PokemonListScreen) { navigator ->
                // O SlideTransition aplica uma animação de transição entre as telas
                SlideTransition(navigator)
            }
        }
    }
}