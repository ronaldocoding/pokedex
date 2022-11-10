package br.com.pokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pokedex.data.mapper.toModel
import br.com.pokedex.domain.interactor.GetSinglePokemonUseCase
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.domain.repository.PokemonRepository
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MIN_POKEMON_ID = 1
private const val MAX_POKEMON_ID = 151

class PokedexViewModel(private val useCase: GetSinglePokemonUseCase) : ViewModel() {

    private val _pokemon = MutableLiveData<List<SinglePokemon>>()
    val pokemon: LiveData<List<SinglePokemon>>
        get() = _pokemon
    private val _pokedexViewState = MutableLiveData<PokedexViewState>()
    val pokedexViewState: LiveData<PokedexViewState>
        get() = _pokedexViewState

    fun getPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = mutableListOf<SinglePokemon>()

            for (i in MIN_POKEMON_ID..MAX_POKEMON_ID) {
                data.add(useCase.execute(i))
            }

            withContext(Dispatchers.Main) {
                _pokemon.postValue(data.toList())
            }
        }
    }

}