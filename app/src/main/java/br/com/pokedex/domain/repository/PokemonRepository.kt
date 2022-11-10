package br.com.pokedex.domain.repository

import br.com.pokedex.data.api.Resource
import br.com.pokedex.data.api.dto.SinglePokemonDTO

interface PokemonRepository {

    suspend fun getSinglePokemon(id: Int): Resource<SinglePokemonDTO>
}