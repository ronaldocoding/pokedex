package br.com.pokedex.domain.repository

import br.com.pokedex.domain.model.SinglePokemon

interface PokemonRepository {

    suspend fun getSinglePokemon(id: Int): SinglePokemon
}