package br.com.pokedex.api.dto

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("official-artwork") val officialArtworkDTO: List<OfficialArtWorkDTO>
)
