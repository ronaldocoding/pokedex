package br.com.pokedex.data.repository

import br.com.pokedex.api.PokemonApi
import br.com.pokedex.data.mapper.toModel
import br.com.pokedex.domain.repository.PokemonRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonRepositoryImpl: PokemonRepository {

    private val api: PokemonApi = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PokemonApi::class.java)

    override suspend fun getSinglePokemon(id: Int) = api.getSinglePokemon(id).toModel()

}