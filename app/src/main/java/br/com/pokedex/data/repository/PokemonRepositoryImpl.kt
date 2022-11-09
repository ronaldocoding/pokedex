package br.com.pokedex.data.repository

import br.com.pokedex.data.api.PokemonApi
import br.com.pokedex.data.mapper.toModel
import br.com.pokedex.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    override suspend fun getSinglePokemon(id: Int) = api.getSinglePokemon(id).toModel()
}