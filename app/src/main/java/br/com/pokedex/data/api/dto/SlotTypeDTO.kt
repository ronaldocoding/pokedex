package br.com.pokedex.data.api.dto

import com.google.gson.annotations.SerializedName

data class SlotTypeDTO(
    @SerializedName("slot") val slot: Int? = null,
    @SerializedName("type") val typeDTO: TypeDTO
)
