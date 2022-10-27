package br.com.pokedex.api

import br.com.pokedex.model.PokemonResponse
import br.com.pokedex.model.SinglePokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon/")
    suspend fun getPokemon(
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): PokemonResponse

    @GET("pokemon/{id}/")
    suspend fun getSinglePokemon(
        @Path("id") id: Int?
    ): SinglePokemonResponse

}