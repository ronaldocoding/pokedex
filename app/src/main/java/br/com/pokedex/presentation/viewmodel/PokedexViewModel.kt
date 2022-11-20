package br.com.pokedex.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.pokedex.domain.interactor.GetSinglePokemonUseCase
import br.com.pokedex.domain.model.SinglePokemon
import kotlinx.coroutines.flow.Flow

class PokedexViewModel(private val useCase: GetSinglePokemonUseCase) : ViewModel() {

    private var currentResult: Flow<PagingData<SinglePokemon>>? = null
    fun getPokemon(searchString: String?): Flow<PagingData<SinglePokemon>> {
        val newResult = useCase.execute(searchString).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}