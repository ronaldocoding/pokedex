package br.com.pokedex.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.databinding.ActivityPokedexBinding
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.util.showIf
import com.google.android.material.progressindicator.CircularProgressIndicator
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexActivity: AppCompatActivity() {

    private val binding by lazy {
        ActivityPokedexBinding.inflate(layoutInflater)
    }

    private val viewModel: PokedexViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupObservers()
        getPokemon()
    }

    private fun setupObservers() {
        viewModel.pokedexViewState.observe(this@PokedexActivity) { viewState ->
            handlePokedexViewState(viewState)
        }
        viewModel.pokemon.observe(this@PokedexActivity) { pokedex ->
            setUpPokedexRecyclerView(pokedex)
        }
    }

    private fun getPokemon() = viewModel.getPokemon()

    private fun handlePokedexViewState(pokedexViewState: PokedexViewState) {
        when (pokedexViewState) {
            PokedexViewState.SUCCESS -> {
                setUpSuccessView()
            }
            PokedexViewState.ERROR -> {
                seUpErrorView()
            }
            PokedexViewState.LOADING -> {
                setUpLoadingView()
            }
        }
    }

    private fun setUpSuccessView() {
        binding.apply {
            pokedexRecyclerView.visibility = RecyclerView.VISIBLE
            pokedexCircularProgressIndicator.visibility = CircularProgressIndicator.GONE
            pokedexErrorMessage.showIf(false)
        }
    }

    private fun seUpErrorView() {
        binding.apply {
            pokedexRecyclerView.visibility = RecyclerView.GONE
            pokedexCircularProgressIndicator.visibility = CircularProgressIndicator.GONE
            pokedexErrorMessage.showIf(true)
        }
    }

    private fun setUpLoadingView() {
        binding.apply {
            pokedexRecyclerView.visibility = RecyclerView.GONE
            pokedexCircularProgressIndicator.visibility = CircularProgressIndicator.VISIBLE
            pokedexErrorMessage.showIf(false)
        }
    }

    private fun setUpPokedexRecyclerView(pokedex: List<SinglePokemon?>?) {
        pokedex?.let { pokemonList ->
            binding.pokedexRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = PokedexAdapter(context, pokemonList)
            }
        }
    }

}