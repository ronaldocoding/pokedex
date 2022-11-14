package br.com.pokedex.presentation.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.R
import br.com.pokedex.databinding.PokedexLoadStateFooterBinding
import br.com.pokedex.util.hideView
import br.com.pokedex.util.showView

class PokedexLoadStateViewHolder(
    private val binding: PokedexLoadStateFooterBinding,
    tryAgain: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.loadStateTryAgainButton.setOnClickListener { tryAgain.invoke() }
    }

    fun bind(loadState: LoadState) {
        when(loadState) {
            is LoadState.Loading -> {
                binding.loadStateProgressBar.showView()
                binding.loadStateErrorMessage.hideView()
                binding.loadStateTryAgainButton.hideView()
            }
            is LoadState.Error -> {
                binding.loadStateErrorMessage.showView()
                binding.loadStateTryAgainButton.showView()
                binding.loadStateProgressBar.hideView()
            }
            is LoadState.NotLoading -> {
                // Do nothing
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup, tryAgain: () -> Unit) : PokedexLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.pokedex_load_state_footer, parent, false)
            val binding = PokedexLoadStateFooterBinding.bind(view)
            return PokedexLoadStateViewHolder((binding), tryAgain)
        }
    }
}