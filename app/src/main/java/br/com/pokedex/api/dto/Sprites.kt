package br.com.pokedex.model

import com.google.gson.annotations.SerializedName

data class Sprites(
    @SerializedName("official-artwork") val officialArtwork: List<OfficialArtWork>
)
