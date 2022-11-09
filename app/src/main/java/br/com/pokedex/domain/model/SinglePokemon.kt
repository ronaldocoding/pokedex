package br.com.pokedex.domain.model

data class SinglePokemon(
    val name: String,
    val id: Int,
    val imageUrl: String,
    val types: List<Type>
)