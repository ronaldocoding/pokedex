package br.com.pokedex.data.api.dto

import com.google.gson.annotations.SerializedName

data class SinglePokemonDTO(
    @SerializedName("name") val name: String? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("weight") val weight: Int? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("sprites") val sprites: SpritesDTO,
    @SerializedName("types") val types: List<SlotTypeDTO>
)