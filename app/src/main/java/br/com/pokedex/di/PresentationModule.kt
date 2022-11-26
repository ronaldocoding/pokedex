package br.com.pokedex.di

import br.com.pokedex.presentation.viewmodel.MainViewModel
import br.com.pokedex.presentation.viewmodel.PokedexViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun presentationModule() = module {
    viewModel { MainViewModel() }
    viewModel { PokedexViewModel(get()) }
}