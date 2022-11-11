package br.com.pokedex.data.api.dto

import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    @SerializedName("results") val results: List<PokemonResultDTO>
)