package br.com.pokedex.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.databinding.PokemonCardBinding
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.util.showIf
import br.com.pokedex.util.showView
import coil.load

class PokedexAdapter(
    private val context: Context
) : PagingDataAdapter<SinglePokemon, PokedexAdapter.PokemonViewHolder>(
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

    inner class PokemonViewHolder(binding: PokemonCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.pokemonImage
        private val name = binding.pokemonName
        private val id = binding.pokemonId
        private val firstType = binding.firstPokemonType
        private val secondType = binding.secondPokemonType

        fun bind(singlePokemon: SinglePokemon) {
            loadPokemonImage(image, singlePokemon.imageUrl)
            name.text = singlePokemon.name
            id.text = singlePokemon.id.toString()
            firstType.text = singlePokemon.types.first().name
            secondType.text = singlePokemon.types.last().name
            secondType.apply {
                showIf(text.isNotEmpty())
            }
        }

        private fun loadPokemonImage(image: ImageView, imageUrl: String) {
            image.load(imageUrl)
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