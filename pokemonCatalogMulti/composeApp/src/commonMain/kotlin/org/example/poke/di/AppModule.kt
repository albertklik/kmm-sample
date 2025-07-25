package org.example.poke.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.example.poke.data.remote.PokemonApi
import org.example.poke.db.AppDatabase
import org.example.poke.domain.repository.PokemonRepository
import org.example.poke.domain.repository.PokemonRepositoryImpl
import org.example.poke.platform.DatabaseDriverFactory
import org.example.poke.presentation.favorites.FavoritesScreenModel
import org.example.poke.presentation.list.PokemonListScreenModel

// Módulo para dependências de dados (API, DB, Repositório)
val dataModule = module {
    single { PokemonApi() }
    single {
        val driver = get<DatabaseDriverFactory>().createDriver()
        AppDatabase(driver)
    }
    single<PokemonRepository> { PokemonRepositoryImpl(get(),get()) }
}

// Módulo para os ScreenModels (ViewModels)
val presentationModule = module {
    factory { PokemonListScreenModel(get()) }
    factory { FavoritesScreenModel(get()) }
}

// Função para ser chamada de cada plataforma
expect fun platformModule(): Module

// Lista de todos os módulos
fun appModule() = listOf(dataModule, presentationModule, platformModule())
