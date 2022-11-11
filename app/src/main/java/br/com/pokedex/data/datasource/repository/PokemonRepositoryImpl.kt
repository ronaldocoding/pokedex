package br.com.pokedex.data.datasource.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.pokedex.data.api.PokemonApi
import br.com.pokedex.data.datasource.Constants
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    override fun getSinglePokemon(): Flow<PagingData<SinglePokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { PokedexPagingSource(api) }
        ).flow
    }
}