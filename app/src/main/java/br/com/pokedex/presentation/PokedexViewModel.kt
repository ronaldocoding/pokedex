package br.com.pokedex.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.pokedex.data.PokemonRepository
import br.com.pokedex.model.SinglePokemonResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokedexViewModel : ViewModel() {

    val pokemon = MutableLiveData<List<SinglePokemonResponse>>()

    fun fetchPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = mutableListOf<SinglePokemonResponse>()

            for (i in 1..151) {
                data.add(PokemonRepository.service.getSinglePokemon(i))
            }

            pokemon.postValue(data.toList())
        }
    }

}