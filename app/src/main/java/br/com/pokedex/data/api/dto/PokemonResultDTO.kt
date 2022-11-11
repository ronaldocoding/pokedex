package br.com.pokedex.data.api.dto

import com.google.gson.annotations.SerializedName

data class PokemonResultDTO(
    @SerializedName("name") val name: String? = null,
)