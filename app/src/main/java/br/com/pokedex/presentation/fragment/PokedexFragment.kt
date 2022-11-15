package br.com.pokedex.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import br.com.pokedex.R
import br.com.pokedex.databinding.FragmentPokedexBinding
import br.com.pokedex.presentation.adapter.PokedexAdapter
import br.com.pokedex.presentation.adapter.PokedexLoadStateAdapter
import br.com.pokedex.presentation.viewmodel.PokedexViewModel
import br.com.pokedex.util.Constants
import br.com.pokedex.util.hideView
import br.com.pokedex.util.showView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SPAN_COUNT = 2

class PokedexFragment : Fragment() {

    private lateinit var appContext: Context
    private val binding by lazy { FragmentPokedexBinding.inflate(layoutInflater) }
    private val pokedexAdapter by lazy { PokedexAdapter(appContext)}
    private val viewModel: PokedexViewModel by viewModel()

    override fun onAttach(context: Context) {
        appContext = context
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBackButton()
        setUpAdapter()
        setUpTryAgainButton()
        setUpPokedexRecyclerView()
        getPokemon()
    }

    private fun setUpBackButton() {
        binding.pokedexBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_pokedexFragment_to_homeFragment)
        }
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
                    return if (viewType == Constants.PRODUCT_VIEW_TYPE) Constants.PRODUCT_VIEW_TYPE
                    else Constants.NETWORK_VIEW_TYPE
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