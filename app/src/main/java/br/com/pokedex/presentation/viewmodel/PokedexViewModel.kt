package br.com.pokedex.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.pokedex.domain.interactor.GetSinglePokemonUseCase
import br.com.pokedex.domain.model.SinglePokemon
import kotlinx.coroutines.flow.Flow

class PokedexViewModel(private val useCase: GetSinglePokemonUseCase) : ViewModel() {

    fun getPokemonFlow(): Flow<PagingData<SinglePokemon>> {
        return useCase.execute().cachedIn(viewModelScope)
    }

}