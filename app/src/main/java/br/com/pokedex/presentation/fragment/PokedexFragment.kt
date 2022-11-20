package br.com.pokedex.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
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
import br.com.pokedex.util.Constants.ONE_SPAN_SIZE
import br.com.pokedex.util.Constants.POKEMON_VIEW_TYPE
import br.com.pokedex.util.Constants.TWO_SPANS_SIZE
import br.com.pokedex.util.emptyString
import br.com.pokedex.util.hideView
import br.com.pokedex.util.showView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val SPAN_COUNT = 2

class PokedexFragment : Fragment() {

    private lateinit var appContext: Context
    private val binding by lazy { FragmentPokedexBinding.inflate(layoutInflater) }
    private val pokedexAdapter by lazy { PokedexAdapter(appContext) }
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
        setUpSearchBar(view)
        getPokemon()
    }

    private fun setUpBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_pokedexFragment_to_homeFragment)
        }
    }

    private fun setUpAdapter() {
        pokedexAdapter.addLoadStateListener { loadState ->
            when (loadState.refresh) {
                is LoadState.Loading -> setUpLoadingView()
                is LoadState.Error -> setUpErrorView()
                else -> setUpSuccessView()
            }
        }
    }

    private fun setUpSuccessView() {
        binding.apply {
            backButton.showView()
            pokedexText.showView()
            searchBar.showView()
            pokedexRecyclerView.showView()
            happyLittenIcon.hideView()
            linearProgressIndicator.hideView()
            neutralLitten.hideView()
            pokedexErrorMessage.hideView()
            tryAgainButton.hideView()
        }
    }

    private fun setUpErrorView() {
        binding.apply {
            backButton.showView()
            pokedexText.showView()
            searchBar.hideView()
            pokedexRecyclerView.hideView()
            happyLittenIcon.hideView()
            linearProgressIndicator.hideView()
            neutralLitten.showView()
            pokedexErrorMessage.showView()
            tryAgainButton.showView()
        }
    }

    private fun setUpLoadingView() {
        binding.apply {
            backButton.hideView()
            pokedexText.hideView()
            searchBar.hideView()
            pokedexRecyclerView.hideView()
            happyLittenIcon.showView()
            linearProgressIndicator.showView()
            neutralLitten.hideView()
            pokedexErrorMessage.hideView()
            tryAgainButton.hideView()
        }
    }

    private fun setUpTryAgainButton() {
        binding.tryAgainButton.setOnClickListener {
            pokedexAdapter.refresh()
        }
    }

    private fun setUpPokedexRecyclerView() {
        binding.pokedexRecyclerView.apply {
            val gridLayoutManager = GridLayoutManager(context, SPAN_COUNT)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val viewType = pokedexAdapter.getItemViewType(position)
                    return if (viewType == POKEMON_VIEW_TYPE) ONE_SPAN_SIZE
                    else TWO_SPANS_SIZE
                }
            }
            layoutManager = gridLayoutManager
            adapter = pokedexAdapter.withLoadStateFooter(
                footer = PokedexLoadStateAdapter { pokedexAdapter.retry() }
            )
        }
    }

    private fun setUpSearchBar(view: View) {
        binding.apply {
            textInputEditText.apply {
                setOnTouchListener { _, _ ->
                    isCursorVisible = true
                    performClick()
                }
                setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        hideKeyboard(view)
                        isCursorVisible = false
                        return@OnEditorActionListener true
                    }
                    false
                })
            }
            searchBar.setOnClickListener {
                showKeyboard()
            }
        }
    }

    private fun hideKeyboard(view: View) {
        val currentView = requireActivity().currentFocus
        currentView?.let {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.textInputEditText.windowToken, 0)
        }
    }

    private fun FragmentPokedexBinding.showKeyboard() {
        textInputEditText.requestFocus()
        textInputEditText.isCursorVisible = true
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(textInputEditText, 0)
    }

    private fun getPokemon() {
        lifecycleScope.launch {
            viewModel.getPokemonFlow().collectLatest { pokemon ->
                pokedexAdapter.submitData(pokemon)
            }
        }
    }

}