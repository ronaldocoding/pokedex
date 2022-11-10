package br.com.pokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pokedex.data.api.Resource
import br.com.pokedex.data.mapper.toModel
import br.com.pokedex.domain.interactor.GetSinglePokemonUseCase
import br.com.pokedex.domain.model.SinglePokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MIN_POKEMON_ID = 1
private const val MAX_POKEMON_ID = 151

class PokedexViewModel(private val useCase: GetSinglePokemonUseCase) : ViewModel() {

    private val _pokemon = MutableLiveData<List<SinglePokemon?>>()
    val pokemon: LiveData<List<SinglePokemon?>>
        get() = _pokemon
    private val _pokedexViewState = MutableLiveData<PokedexViewState>()
    val pokedexViewState: LiveData<PokedexViewState>
        get() = _pokedexViewState

    fun getPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = mutableListOf<SinglePokemon?>()

            _pokedexViewState.postValue(PokedexViewState.LOADING)

            for (i in MIN_POKEMON_ID..MAX_POKEMON_ID) {
                when(val response = useCase.execute(i)) {
                    is Resource.Success -> {
                        data.add(response.data?.toModel())
                        if(i == MAX_POKEMON_ID)
                            _pokedexViewState.postValue(PokedexViewState.SUCCESS)
                    }
                    is Resource.Error -> {
                        _pokedexViewState.postValue(PokedexViewState.ERROR)
                    }
                    is Resource.Loading -> {
                        _pokedexViewState.postValue(PokedexViewState.LOADING)
                    }
                }
            }

            withContext(Dispatchers.Main) {
                _pokemon.postValue(data.toList())
            }
        }
    }

}