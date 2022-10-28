package br.com.pokedex.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pokedex.data.PokemonRepository
import br.com.pokedex.model.SinglePokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val MIN_POKEMON_ID = 1
private const val MAX_POKEMON_ID = 151

class PokedexViewModel : ViewModel() {

    private val repository = PokemonRepository()
    private val _pokemon = MutableLiveData<List<SinglePokemonResponse>>()
    val pokemon: LiveData<List<SinglePokemonResponse>>
        get() = _pokemon

    fun getPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = mutableListOf<SinglePokemonResponse>()

            for (i in MIN_POKEMON_ID..MAX_POKEMON_ID) {
                data.add(repository.getSinglePokemon(i))
            }

            withContext(Dispatchers.Main) {
                _pokemon.postValue(data.toList())
            }
        }
    }

}