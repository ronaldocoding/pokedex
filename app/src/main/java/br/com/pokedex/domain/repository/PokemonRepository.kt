package br.com.pokedex.domain.repository

import androidx.paging.PagingData
import br.com.pokedex.data.api.Resource
import br.com.pokedex.data.api.dto.SinglePokemonDTO
import br.com.pokedex.domain.model.SinglePokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getSinglePokemon(): Flow<PagingData<SinglePokemon>>
}