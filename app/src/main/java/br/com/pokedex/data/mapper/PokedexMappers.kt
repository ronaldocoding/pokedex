package br.com.pokedex.data.mapper

import br.com.pokedex.data.api.dto.SinglePokemonDTO
import br.com.pokedex.data.api.dto.SlotTypeDTO
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.domain.model.Type
import br.com.pokedex.util.emptyString
import br.com.pokedex.util.zeroNumber

fun SlotTypeDTO.toModel() = Type(
    name = typeDTO.name ?: emptyString()
)

fun List<SlotTypeDTO>.toModel(): List<Type> {
    val types = mutableListOf<Type>()
    types.add(this.first().toModel())
    this.first().let { firstType ->
        this.last().let { secondType ->
            if(secondType != firstType) {
                types.add(secondType.toModel())
            } else {
                types.add(Type(emptyString()))
            }
        }
    }
    return types.toList()
}

fun SinglePokemonDTO.toModel() = SinglePokemon(
    name = name ?: emptyString(),
    id = id ?: zeroNumber(),
    imageUrl = sprites.other.officialArtworkDTO.frontDefault ?: emptyString(),
    types = types.toModel()
)