package br.com.pokedex.presentation.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import br.com.pokedex.presentation.viewholder.PokedexLoadStateViewHolder

class PokedexLoadStateAdapter(
    private val tryAgain: () -> Unit
) : LoadStateAdapter<PokedexLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PokedexLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PokedexLoadStateViewHolder {
        return PokedexLoadStateViewHolder.create(parent, tryAgain)
    }

}