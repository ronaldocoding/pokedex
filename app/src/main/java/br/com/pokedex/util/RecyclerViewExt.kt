package br.com.pokedex.util

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.showView() {
    visibility = RecyclerView.VISIBLE
}

fun RecyclerView.hideView() {
    visibility = RecyclerView.GONE
}