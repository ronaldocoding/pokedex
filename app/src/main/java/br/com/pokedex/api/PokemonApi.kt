package br.com.pokedex.api

import br.com.pokedex.api.dto.PokemonDTO
import br.com.pokedex.api.dto.SinglePokemonDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon/")
    suspend fun getPokemon(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonDTO

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: Int?
    ): SinglePokemonDTO

}