package br.com.pokedex.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokedex.databinding.ActivityPokedexBinding
import br.com.pokedex.model.SinglePokemon

class PokedexActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityPokedexBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: PokedexViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PokedexViewModel::class.java]

        viewModel.getPokemon()

        viewModel.pokemon.observe(this@PokedexActivity) { pokedex ->
            setUpPokedexRecyclerView(pokedex)
        }
    }

    private fun setUpPokedexRecyclerView(pokedex: List<SinglePokemon>?) {
        pokedex?.let { pokemonList ->
            binding.pokedexRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = PokedexAdapter(context, pokemonList)
            }
        }
    }

}