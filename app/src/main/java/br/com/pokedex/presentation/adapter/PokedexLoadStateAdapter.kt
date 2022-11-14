package br.com.pokedex.presentation

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

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