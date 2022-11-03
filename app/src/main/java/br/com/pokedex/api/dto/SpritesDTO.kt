package br.com.pokedex.api.dto

import com.google.gson.annotations.SerializedName

data class SpritesDTO(
    @SerializedName("other") val other: OtherDTO
)
