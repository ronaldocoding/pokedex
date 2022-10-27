package br.com.pokedex.model

import com.google.gson.annotations.SerializedName

data class PokemonResult(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
)