package org.example.poke.domain.repository

import domain.model.Pokemon

interface PokemonRepository {
    suspend fun getPokemonList(page: Int): Result<List<Pokemon>>
    suspend fun getPokemonDetails(name: String): Result<Pokemon>
    suspend fun getFavoritePokemons(): List<Pokemon>
    suspend fun isFavorite(id: Int): Boolean
    suspend fun addFavorite(pokemon: Pokemon)
    suspend fun removeFavorite(id: Int)
}
