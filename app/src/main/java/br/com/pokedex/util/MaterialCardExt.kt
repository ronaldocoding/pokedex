package br.com.pokedex.util

import com.google.android.material.card.MaterialCardView

fun MaterialCardView.hideView() {
    visibility = MaterialCardView.GONE
}

fun MaterialCardView.showView() {
    visibility = MaterialCardView.VISIBLE
}