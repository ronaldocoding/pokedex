package br.com.pokedex.model

import com.google.gson.annotations.SerializedName

data class OfficialArtWork(
    @SerializedName("front-default") val frontDefault: String? = null
)
