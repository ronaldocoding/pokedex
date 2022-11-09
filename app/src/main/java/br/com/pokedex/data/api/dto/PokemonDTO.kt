package br.com.pokedex.data.api.dto

import com.google.gson.annotations.SerializedName

data class PokemonDTO(
    @SerializedName("count") val count: Int? = null,
    @SerializedName("next") val next: String? = null,
    @SerializedName("previous") val previous: String? = null,
    @SerializedName("results") val results: List<PokemonResultDTO>
)