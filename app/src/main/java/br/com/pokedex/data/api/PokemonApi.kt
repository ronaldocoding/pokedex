package br.com.pokedex.data.api

import br.com.pokedex.data.api.dto.PokemonDTO
import br.com.pokedex.data.api.dto.SinglePokemonDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon/")
    suspend fun getPokemon(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonDTO

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: Int?
    ): Response<SinglePokemonDTO>

}