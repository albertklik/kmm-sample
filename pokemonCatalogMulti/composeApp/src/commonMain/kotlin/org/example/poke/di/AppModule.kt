package org.example.poke.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

// Módulos para ViewModels, Repositórios, etc.
val appModule = module {
    // Exemplo: singleOf(::MyViewModel)
    // Exemplo: singleOf(::MyRepository)
}
