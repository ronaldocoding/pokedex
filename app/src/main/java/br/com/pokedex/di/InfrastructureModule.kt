package br.com.pokedex.di

import br.com.pokedex.BuildConfig
import br.com.pokedex.data.api.PokemonApi
import br.com.pokedex.data.repository.PokemonRepositoryImpl
import br.com.pokedex.domain.repository.PokemonRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun infrastructureModule() = module {
    factory { provideRetrofit() }
    factory { providePokemonApi(get()) }
    factory<PokemonRepository> { PokemonRepositoryImpl(get()) }
}

private fun providePokemonApi(retrofit: Retrofit): PokemonApi {
    return retrofit.create(PokemonApi::class.java)
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder().run {
        addConverterFactory(GsonConverterFactory.create())
        baseUrl(BuildConfig.POKE_API)
        build()
    }
}