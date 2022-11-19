package br.com.pokedex.util

import com.google.android.material.progressindicator.LinearProgressIndicator

fun LinearProgressIndicator.showView() {
    visibility = LinearProgressIndicator.VISIBLE
}

fun LinearProgressIndicator.hideView() {
    visibility = LinearProgressIndicator.GONE
}