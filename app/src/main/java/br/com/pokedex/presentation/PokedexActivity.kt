package br.com.pokedex.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import br.com.pokedex.databinding.ActivityPokedexBinding
import br.com.pokedex.util.Constants.NETWORK_VIEW_TYPE
import br.com.pokedex.util.Constants.PRODUCT_VIEW_TYPE
import br.com.pokedex.util.hideView
import br.com.pokedex.util.showView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPAN_COUNT = 2

class PokedexActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPokedexBinding.inflate(layoutInflater) }
    private val pokedexAdapter by lazy { PokedexAdapter(this) }
    private val viewModel: PokedexViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpAdapter()
        setUpTryAgainButton()
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
            pokedexTryAgainButton.hideView()
        }
    }

    private fun setUpErrorView() {
        binding.apply {
            pokedexRecyclerView.hideView()
            pokedexCircularProgressIndicator.hideView()
            pokedexErrorMessage.showView()
            pokedexTryAgainButton.showView()
        }
    }

    private fun setUpLoadingView() {
        binding.apply {
            pokedexRecyclerView.hideView()
            pokedexCircularProgressIndicator.showView()
            pokedexErrorMessage.hideView()
            pokedexTryAgainButton.hideView()
        }
    }

    private fun setUpTryAgainButton() {
        binding.pokedexTryAgainButton.setOnClickListener {
            pokedexAdapter.refresh()
        }
    }

    private fun setUpPokedexRecyclerView() {
        binding.pokedexRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, SPAN_COUNT)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = pokedexAdapter.getItemViewType(position)
                    return if (viewType == PRODUCT_VIEW_TYPE) PRODUCT_VIEW_TYPE
                    else NETWORK_VIEW_TYPE
                }
            }
            layoutManager = gridLayoutManager
            adapter = pokedexAdapter.withLoadStateFooter(
                footer = PokedexLoadStateAdapter { pokedexAdapter.retry() }
            )
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