package br.com.pokedex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.pokedex.data.api.PokemonApi
import br.com.pokedex.util.Constants.PAGE_SIZE
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(private val api: PokemonApi) : PokemonRepository {

    override fun getSinglePokemon(searchString: String?): Flow<PagingData<SinglePokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { PokedexPagingSource(api, searchString) }
        ).flow
    }
}