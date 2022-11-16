package br.com.pokedex.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.pokedex.databinding.PokemonCardBinding
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.presentation.viewholder.PokemonViewHolder
import br.com.pokedex.util.Constants.NETWORK_VIEW_TYPE
import br.com.pokedex.util.Constants.POKEMON_VIEW_TYPE

class PokedexAdapter(
    private val context: Context
) : PagingDataAdapter<SinglePokemon, PokemonViewHolder>(
    POKEMON_COMPARATOR
) {

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val singlePokemon = getItem(position)
        singlePokemon?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonCardBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount) {
            NETWORK_VIEW_TYPE
        } else {
            POKEMON_VIEW_TYPE
        }
    }

    companion object {
        private val POKEMON_COMPARATOR = object
            : DiffUtil.ItemCallback<SinglePokemon>() {

            override fun areItemsTheSame(
                oldItem: SinglePokemon,
                newItem: SinglePokemon
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SinglePokemon,
                newItem: SinglePokemon
            ): Boolean =
                oldItem == newItem
        }
    }

}