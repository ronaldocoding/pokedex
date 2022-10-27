package br.com.pokedex.model

import com.google.gson.annotations.SerializedName

data class SlotType(
    @SerializedName("slot") val slot: Int? = null,
    @SerializedName("type") val type: Type
)
