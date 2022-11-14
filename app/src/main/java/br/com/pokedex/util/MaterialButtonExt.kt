package br.com.pokedex.util

import com.google.android.material.button.MaterialButton

fun MaterialButton.showView() {
    visibility = MaterialButton.VISIBLE
}

fun MaterialButton.hideView() {
    visibility = MaterialButton.GONE
}