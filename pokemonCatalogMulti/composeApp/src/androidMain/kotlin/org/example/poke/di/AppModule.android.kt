package org.example.poke.di

import org.example.poke.platform.DatabaseDriverFactory
import org.koin.dsl.module

actual fun platformModule() = module {
    single { DatabaseDriverFactory(get()) } // 'get()' aqui resolve o Context do Android
}