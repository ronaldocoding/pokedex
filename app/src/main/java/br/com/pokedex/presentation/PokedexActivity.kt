package br.com.pokedex.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.pokedex.databinding.ActivityPokedexBinding

class PokedexActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityPokedexBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: PokedexViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokedexViewModel::class.java]

        viewModel.fetchPokemon()

        viewModel.pokemon.observe(this@PokedexActivity) { pokedex ->
            pokedex.forEach { pokemon ->
                Log.i("POKEMON", "${pokemon.name}")
            }
        }
    }

}