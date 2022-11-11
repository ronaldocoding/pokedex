package br.com.pokedex.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.pokedex.databinding.ActivityPokedexBinding
import br.com.pokedex.util.hideView
import br.com.pokedex.util.showView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokedexActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPokedexBinding.inflate(layoutInflater)
    }

    private val viewModel: PokedexViewModel by viewModel()
    private val pokedexAdapter by lazy {
        PokedexAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAdapter()
        setUpPokedexRecyclerView()
        getPokemon()
    }

    private fun setUpAdapter() {
        pokedexAdapter.addLoadStateListener { loadState ->
            when(loadState.refresh) {
                is LoadState.Loading -> setUpLoadingView()
                is LoadState.Error -> setUpErrorView()
                else -> setUpSuccessView()
            }
        }
    }

    private fun setUpSuccessView() {
        binding.apply {
            pokedexRecyclerView.showView()
            pokedexCircularProgressIndicator.hideView()
            pokedexErrorMessage.hideView()
        }
    }

    private fun setUpErrorView() {
        binding.apply {
            pokedexRecyclerView.hideView()
            pokedexCircularProgressIndicator.hideView()
            pokedexErrorMessage.showView()
        }
    }

    private fun setUpLoadingView() {
        binding.apply {
            pokedexRecyclerView.hideView()
            pokedexCircularProgressIndicator.showView()
            pokedexErrorMessage.hideView()
        }
    }

    private fun setUpPokedexRecyclerView() {
        binding.pokedexRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pokedexAdapter
        }
    }

    private fun getPokemon() {
        lifecycleScope.launch {
            viewModel.getPokemonFlow().collectLatest { pokemon ->
                pokedexAdapter.submitData(pokemon)
            }
        }
    }

}