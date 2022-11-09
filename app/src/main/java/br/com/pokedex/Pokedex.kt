package br.com.pokedex

import android.app.Application
import br.com.pokedex.di.inject

class Pokedex : Application() {

    override fun onCreate() {
        super.onCreate()
        inject()
    }
}