package br.com.pokedex.domain.interactor

import br.com.pokedex.domain.repository.PokemonRepository

class GetSinglePokemonUseCase(private val repository: PokemonRepository) {

    suspend fun execute(id: Int) = repository.getSinglePokemon(id)
}