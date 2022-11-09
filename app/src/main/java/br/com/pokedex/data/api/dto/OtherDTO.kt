package br.com.pokedex.data.api.dto

import com.google.gson.annotations.SerializedName

data class OtherDTO(
    @SerializedName("official-artwork") val officialArtworkDTO: OfficialArtWorkDTO
)