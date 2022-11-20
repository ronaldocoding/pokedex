package br.com.pokedex.util

import androidx.constraintlayout.widget.ConstraintLayout

fun ConstraintLayout.hideView() {
    visibility = ConstraintLayout.GONE
}

fun ConstraintLayout.showView() {
    visibility = ConstraintLayout.VISIBLE
}