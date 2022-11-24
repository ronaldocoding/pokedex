package br.com.pokedex.presentation.fragment

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


private const val SPAN_COUNT = 2

class PokedexFragment : Fragment() {

    private var hasInitiatedInitialCall = false
    private lateinit var appContext: Context
    private val binding by lazy { FragmentPokedexBinding.inflate(layoutInflater) }
    private val pokedexAdapter by lazy { PokedexAdapter(appContext) }
    private val viewModel: PokedexViewModel by viewModel()
    private var job: Job? = null

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
        setUpSearchBar()
        setUpOpenSearchBarButton()
        setUpCloseSearchBarButton()
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
            if (
                loadState.source.append is LoadState.NotLoading &&
                loadState.source.append.endOfPaginationReached &&
                pokedexAdapter.itemCount == 0
            ) {
                setUpNoPokemonFoundView()
            }
        }
    }

    private fun setUpLoadingView() {
        binding.apply {
            if (
                magnifierToOpenSearchBar.visibility == ConstraintLayout.GONE &&
                searchBar.visibility == ConstraintLayout.GONE
            ) {
                loadingLayout.showView()
                pokedexRecyclerView.hideView()
                backButton.hideView()
                pokedexText.hideView()
                errorLayout.hideView()
                tryAgainButton.hideView()
                noPokemonFoundLayout.hideView()
            } else {
                setUpSuccessView()
            }
        }
    }

    private fun setUpErrorView() {
        binding.apply {
            backButton.showView()
            pokedexText.showView()
            searchBar.hideView()
            magnifierToOpenSearchBar.hideView()
            pokedexRecyclerView.hideView()
            loadingLayout.hideView()
            errorLayout.showView()
            tryAgainButton.showView()
            noPokemonFoundLayout.hideView()
        }
    }

    private fun setUpSuccessView() {
        binding.apply {
            if (searchBar.visibility == ConstraintLayout.VISIBLE) {
                backButton.hideView()
                pokedexText.hideView()
                magnifierToOpenSearchBar.hideView()
            } else {
                backButton.showView()
                pokedexText.showView()
                magnifierToOpenSearchBar.showView()
            }
            pokedexRecyclerView.showView()
            loadingLayout.hideView()
            errorLayout.hideView()
            tryAgainButton.hideView()
            noPokemonFoundLayout.hideView()
        }
    }

    private fun setUpNoPokemonFoundView() {
        binding.apply {
            backButton.showView()
            pokedexText.showView()
            magnifierToOpenSearchBar
            pokedexRecyclerView.hideView()
            loadingLayout.hideView()
            errorLayout.hideView()
            tryAgainButton.hideView()
            noPokemonFoundLayout.showView()
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

        if (!hasInitiatedInitialCall) {
            startFetchingPokemon(null, false)
            hasInitiatedInitialCall = true
        }

    }

    private fun setUpSearchBar() {
        binding.apply {
            textInputEditText.apply {
                setOnTouchListener { _, _ ->
                    isCursorVisible = true
                    performClick()
                }
                setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        hideKeyboard()
                        performSearch(text.toString().trim())
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

    private fun hideKeyboard() {
        binding.textInputEditText.apply {
            val imm =
                requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.windowToken, 0)
            this.clearFocus()
        }
    }

    private fun showKeyboard() {
        binding.apply {
            textInputEditText.apply {
                requestFocus()
                isCursorVisible = true
                val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                val currentView = requireActivity().currentFocus
                imm.showSoftInput(currentView, 0)
            }
        }
    }

    private fun setUpOpenSearchBarButton() {
        binding.apply {
            magnifierToOpenSearchBar.setOnClickListener {
                backButton.hideView()
                pokedexText.hideView()
                magnifierToOpenSearchBar.hideView()
                searchBar.showView()
                closeIcon.showView()
                showKeyboard()
            }
        }
    }

    private fun setUpCloseSearchBarButton() {
        binding.apply {
            closeIcon.setOnClickListener {
                searchBar.hideView()
                closeIcon.hideView()
                backButton.showView()
                pokedexText.showView()
                magnifierToOpenSearchBar.showView()
                textInputEditText.setText(emptyString())
                hideKeyboard()
            }
        }
    }

    private fun performSearch(searchString: String) {
        startFetchingPokemon(searchString, true)
    }

    private fun startFetchingPokemon(searchString: String?, shouldSubmitEmpty: Boolean) {
        job?.cancel()
        job = lifecycleScope.launch {
            if (shouldSubmitEmpty) {
                pokedexAdapter.submitData(PagingData.empty())
            }
            getPokemon(searchString)
        }
    }

    private fun getPokemon(searchString: String?) {
        lifecycleScope.launch {
            viewModel.getPokemon(searchString).collectLatest { pokemon ->
                pokedexAdapter.submitData(pokemon)
            }
        }
    }

}