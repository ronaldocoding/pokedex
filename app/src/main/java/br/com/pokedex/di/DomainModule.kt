package br.com.pokedex.di

import br.com.pokedex.domain.interactor.GetSinglePokemonUseCase
import org.koin.dsl.module

fun domainModule() = module {
    factory { GetSinglePokemonUseCase(get()) }
}