package br.com.pokedex.api.dto

import com.google.gson.annotations.SerializedName

data class PokemonResultDTO(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)