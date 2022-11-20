package br.com.pokedex.domain.interactor

import br.com.pokedex.domain.repository.PokemonRepository

class GetSinglePokemonUseCase(private val repository: PokemonRepository) {

    fun execute(searchString: String?) = repository.getSinglePokemon(searchString)
}