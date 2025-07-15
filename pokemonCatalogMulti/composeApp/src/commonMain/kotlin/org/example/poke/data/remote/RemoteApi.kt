package org.example.poke.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// DTOs (Data Transfer Objects)
@Serializable
data class PokemonListResponse(val results: List<PokemonResult>)

@Serializable
data class PokemonResult(val name: String, val url: String)

@Serializable
data class PokemonDetailsResponse(val id: Int, val name: String, val sprites: Sprites)

@Serializable
data class Sprites(val other: OtherSprites)

@Serializable
data class OtherSprites(val official_artwork: OfficialArtwork)

@Serializable
data class OfficialArtwork(val front_default: String)


// API Client
class PokemonApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getPokemonList(limit: Int, offset: Int): PokemonListResponse {
        return client.get("https://pokeapi.co/api/v2/pokemon?limit=$limit&offset=$offset").body()
    }

    suspend fun getPokemonDetails(name: String): PokemonDetailsResponse {
        return client.get("https://pokeapi.co/api/v2/pokemon/$name").body()
    }
}
