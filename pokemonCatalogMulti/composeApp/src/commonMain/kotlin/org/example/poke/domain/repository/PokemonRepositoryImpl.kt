package org.example.poke.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.poke.data.remote.PokemonApi
import org.example.poke.db.AppDatabase
import org.example.poke.domain.model.Pokemon

class PokemonRepositoryImpl(
    private val api: PokemonApi,
    private val db: AppDatabase
) : PokemonRepository {

    private val pokemonQueries = db.appDatabaseQueries
    private val POKEMON_PAGE_SIZE = 20

    override suspend fun getPokemonList(page: Int): Result<List<Pokemon>> {
        return try {
            val offset = page * POKEMON_PAGE_SIZE
            val response = api.getPokemonList(limit = POKEMON_PAGE_SIZE, offset = offset)
            val pokemonDetails = response.results.mapNotNull { result ->
                getPokemonDetails(result.name).getOrNull()
            }
            Result.success(pokemonDetails)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPokemonDetails(name: String): Result<Pokemon> {
        return try {
            val details = api.getPokemonDetails(name)
            val isFavorite = isFavorite(details.id)
            val pokemon = Pokemon(
                id = details.id,
                name = details.name.replaceFirstChar { it.uppercase() },
                imageUrl = details.sprites.front_default,
                isFavorite = isFavorite
            )
            Result.success(pokemon)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getFavoritePokemons(): List<Pokemon> {
        return withContext(Dispatchers.Default) {
            pokemonQueries.selectAll().executeAsList().map { entity ->
                Pokemon(
                    id = entity.id.toInt(),
                    name = entity.name,
                    imageUrl = entity.imageUrl,
                    isFavorite = true
                )
            }
        }
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return withContext(Dispatchers.Default) {
            pokemonQueries.selectById(id.toLong()).executeAsOne()
        }
    }

    override suspend fun addFavorite(pokemon: Pokemon) {
        withContext(Dispatchers.Default) {
            pokemonQueries.insert(
                id = pokemon.id.toLong(),
                name = pokemon.name,
                imageUrl = pokemon.imageUrl
            )
        }
    }

    override suspend fun removeFavorite(id: Int) {
        withContext(Dispatchers.Default) {
            pokemonQueries.deleteById(id.toLong())
        }
    }
}