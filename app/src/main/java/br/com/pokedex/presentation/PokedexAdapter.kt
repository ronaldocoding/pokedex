package br.com.pokedex.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.databinding.PokemonCardBinding
import br.com.pokedex.model.SinglePokemon
import br.com.pokedex.util.showIf
import coil.load

class PokedexAdapter(
    private val context: Context,
    private val pokemon: List<SinglePokemon>
) : RecyclerView.Adapter<PokedexAdapter.PokemonViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            PokemonCardBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemon[position])
    }

    override fun getItemCount() = pokemon.size
}