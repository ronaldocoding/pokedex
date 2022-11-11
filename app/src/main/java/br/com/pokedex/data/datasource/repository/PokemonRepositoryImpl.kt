package br.com.pokedex.data.repository

import br.com.pokedex.data.api.PokemonApi
import br.com.pokedex.data.api.Resource
import br.com.pokedex.data.api.dto.SinglePokemonDTO
import br.com.pokedex.domain.repository.PokemonRepository

class PokemonRepositoryImpl(private val api: PokemonApi) : BaseRepositoryImpl(), PokemonRepository {

    override suspend fun getSinglePokemon(id: Int): Resource<SinglePokemonDTO> {
        return safeApiCall { api.getSinglePokemon(id) }
    }
}