package br.com.pokedex.data.datasource.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.pokedex.data.api.PokemonApi
import br.com.pokedex.util.Constants.LAST_OFFSET
import br.com.pokedex.util.Constants.LAST_POSITION
import br.com.pokedex.util.Constants.POKEMON_OFFSET
import br.com.pokedex.util.Constants.POKEMON_STARTING_OFFSET
import br.com.pokedex.data.mapper.toModel
import br.com.pokedex.domain.model.SinglePokemon
import okio.IOException
import retrofit2.HttpException

class PokedexPagingSource(
    private val api: PokemonApi
) : PagingSource<Int, SinglePokemon>() {

    override fun getRefreshKey(state: PagingState<Int, SinglePokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SinglePokemon> {
        val position = params.key ?: POKEMON_STARTING_OFFSET
        return try {
            val response = api.getPokemon(
                if (position == LAST_POSITION) {
                    LAST_OFFSET
                } else {
                    position * POKEMON_OFFSET
                }
            )
            val pokemon = mutableListOf<SinglePokemon>()
            response.body()?.results?.map { result ->
                val singlePokemon = api.getSinglePokemon(result.name)
                singlePokemon.body()?.toModel()?.let { pokemon.add(it) }
            }
            LoadResult.Page(
                data = pokemon,
                prevKey = if (position == POKEMON_STARTING_OFFSET) null else position,
                nextKey = if (position == LAST_POSITION) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}