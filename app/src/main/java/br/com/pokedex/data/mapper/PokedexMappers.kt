package br.com.pokedex.data.mapper

import br.com.pokedex.api.dto.SinglePokemonDTO
import br.com.pokedex.api.dto.SlotTypeDTO
import br.com.pokedex.model.SinglePokemon
import br.com.pokedex.model.Type
import br.com.pokedex.util.emptyString
import br.com.pokedex.util.zeroNumber

fun SlotTypeDTO.toModel() = Type(
    name = typeDTO.name ?: emptyString()
)

fun List<SlotTypeDTO>.toModel(): List<Type> {
    val types = mutableListOf<Type>()
    forEach { slot ->
        types.add(slot.toModel())
    }
    return types.toList()
}

fun SinglePokemonDTO.toModel() = SinglePokemon(
    name = name ?: emptyString(),
    id = id ?: zeroNumber(),
    imageUrl = sprites.officialArtworkDTO[zeroNumber()].frontDefault ?: emptyString(),
    types = types.toModel()
)