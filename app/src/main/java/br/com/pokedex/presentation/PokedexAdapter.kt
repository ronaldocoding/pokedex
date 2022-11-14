package br.com.pokedex.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import br.com.pokedex.databinding.PokemonCardBinding
import br.com.pokedex.domain.model.SinglePokemon

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