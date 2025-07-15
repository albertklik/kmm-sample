package org.example.poke.di

import org.koin.core.module.Module
import org.koin.dsl.module
import data.remote.PokemonApi
import data.repository.PokemonRepositoryImpl
import domain.repository.PokemonRepository
import presentation.favorites.FavoritesScreenModel
import presentation.list.PokemonListScreenModel
import platform.DatabaseDriverFactory
import com.example.project.db.AppDatabase
import org.example.poke.data.remote.PokemonApi
import org.example.poke.db.AppDatabase
import org.example.poke.platform.DatabaseDriverFactory

// Módulo para dependências de dados (API, DB, Repositório)
val dataModule = module {
    single { PokemonApi() }
    single {
        val driver = get<DatabaseDriverFactory>().createDriver()
        AppDatabase(driver)
    }
    single<PokemonRepository> { PokemonRepositoryImpl(get(), get()) }
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
