package br.com.pokedex.presentation.viewholder

import android.content.Context
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import br.com.pokedex.R
import br.com.pokedex.databinding.PokemonCardBinding
import br.com.pokedex.domain.model.SinglePokemon
import br.com.pokedex.util.Constants.BUG
import br.com.pokedex.util.Constants.DARK
import br.com.pokedex.util.Constants.DRAGON
import br.com.pokedex.util.Constants.ELECTRIC
import br.com.pokedex.util.Constants.FAIRY
import br.com.pokedex.util.Constants.FIGHTING
import br.com.pokedex.util.Constants.FIRE
import br.com.pokedex.util.Constants.FLYING
import br.com.pokedex.util.Constants.GHOST
import br.com.pokedex.util.Constants.GRASS
import br.com.pokedex.util.Constants.GROUND
import br.com.pokedex.util.Constants.ICE
import br.com.pokedex.util.Constants.NORMAL
import br.com.pokedex.util.Constants.POISON
import br.com.pokedex.util.Constants.PSYCHIC
import br.com.pokedex.util.Constants.ROCK
import br.com.pokedex.util.Constants.STEEL
import br.com.pokedex.util.Constants.WATER
import br.com.pokedex.util.hideView
import br.com.pokedex.util.toIdForm
import coil.load

class PokemonViewHolder(
    binding: PokemonCardBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    private val image = binding.pokemonImage
    private val name = binding.pokemonName
    private val id = binding.pokemonId
    private val firstType = binding.firstTypeIcon
    private val secondType = binding.secondTypeIcon
    private val pokemonCard = binding.pokemonCard

    fun bind(singlePokemon: SinglePokemon) {
        loadPokemonImage(image, singlePokemon.imageUrl)
        name.text = singlePokemon.name.replaceFirstChar { it.uppercase() }
        id.text = singlePokemon.id.toString().toIdForm()
        val firstTypeName = singlePokemon.types.first().name
        val secondTypeName = singlePokemon.types.last().name
        setUpPokemonCardColors(firstTypeName, secondTypeName)
    }

    private fun setUpPokemonCardColors(firstTypeName: String, secondTypeName: String) {
        when (firstTypeName) {
            BUG -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.dark_palm_leaf))
                firstType.setImageResource(R.drawable.ic_bug_type)
            }
            DARK -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.dark_liver))
                firstType.setImageResource(R.drawable.ic_dark_type)
            }
            DRAGON -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.dark_salmon))
                firstType.setImageResource(R.drawable.ic_dragon_type)
            }
            ELECTRIC -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.cyber_yellow))
                firstType.setImageResource(R.drawable.ic_electric_type)
            }
            FAIRY -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.lavender_rose))
                firstType.setImageResource(R.drawable.ic_fairy_type)
            }
            FIGHTING -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.alloy_orange))
                firstType.setImageResource(R.drawable.ic_fighting_type)
            }
            FIRE -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.light_fire_opal))
                firstType.setImageResource(R.drawable.ic_fire_type)
            }
            FLYING -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.brilliant_azure))
                firstType.setImageResource(R.drawable.ic_flying_type)
            }
            GHOST -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.dark_lavender))
                firstType.setImageResource(R.drawable.ic_ghost_type)
            }
            GRASS -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.emerald))
                firstType.setImageResource(R.drawable.ic_grass_type)
            }
            GROUND -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.jonquil))
                firstType.setImageResource(R.drawable.ic_ground_type)
            }
            ICE -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.moonstone))
                firstType.setImageResource(R.drawable.ic_ice_type)
            }
            NORMAL -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.philippine_gray))
                firstType.setImageResource(R.drawable.ic_normal_type)
            }
            POISON -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.purple_pizzazz))
                firstType.setImageResource(R.drawable.ic_poison_type)
            }
            PSYCHIC -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.middle_purple))
                firstType.setImageResource(R.drawable.ic_psychic_type)
            }
            ROCK -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.light_gold))
                firstType.setImageResource(R.drawable.ic_rock_type)
            }
            STEEL -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.light_aurometalsaurus))
                firstType.setImageResource(R.drawable.ic_steel_type)
            }
            WATER -> {
                pokemonCard.setCardBackgroundColor(context.resources.getColor(R.color.cyan_blue_azure))
                firstType.setImageResource(R.drawable.ic_water_type)
            }
        }

        if (secondTypeName.isEmpty()) {
            secondType.hideView()
        } else {
            when (secondTypeName) {
                BUG -> {
                    secondType.setImageResource(R.drawable.ic_bug_type)
                }
                DARK -> {
                    secondType.setImageResource(R.drawable.ic_dark_type)
                }
                DRAGON -> {
                    secondType.setImageResource(R.drawable.ic_dragon_type)
                }
                ELECTRIC -> {
                    secondType.setImageResource(R.drawable.ic_electric_type)
                }
                FAIRY -> {
                    secondType.setImageResource(R.drawable.ic_fairy_type)
                }
                FIGHTING -> {
                    secondType.setImageResource(R.drawable.ic_fighting_type)
                }
                FIRE -> {
                    secondType.setImageResource(R.drawable.ic_fire_type)
                }
                FLYING -> {
                    secondType.setImageResource(R.drawable.ic_flying_type)
                }
                GHOST -> {
                    secondType.setImageResource(R.drawable.ic_ghost_type)
                }
                GRASS -> {
                    secondType.setImageResource(R.drawable.ic_grass_type)
                }
                GROUND -> {
                    secondType.setImageResource(R.drawable.ic_ground_type)
                }
                ICE -> {
                    secondType.setImageResource(R.drawable.ic_ice_type)
                }
                NORMAL -> {
                    secondType.setImageResource(R.drawable.ic_normal_type)
                }
                POISON -> {
                    secondType.setImageResource(R.drawable.ic_poison_type)
                }
                PSYCHIC -> {
                    secondType.setImageResource(R.drawable.ic_psychic_type)
                }
                ROCK -> {
                    secondType.setImageResource(R.drawable.ic_rock_type)
                }
                STEEL -> {
                    secondType.setImageResource(R.drawable.ic_steel_type)
                }
                WATER -> {
                    secondType.setImageResource(R.drawable.ic_water_type)
                }
            }
        }
    }

    private fun loadPokemonImage(image: ImageView, imageUrl: String) {
        image.load(imageUrl)
    }
}