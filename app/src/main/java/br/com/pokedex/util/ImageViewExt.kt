package br.com.pokedex.util

import android.widget.ImageView

fun ImageView.hideView() {
    visibility = ImageView.GONE
}

fun ImageView.showView() {
    visibility = ImageView.VISIBLE
}

fun ImageView.isGone() = visibility == ImageView.GONE