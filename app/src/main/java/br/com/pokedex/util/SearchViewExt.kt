package br.com.pokedex.util

import androidx.appcompat.widget.SearchView

fun SearchView.hideView() {
    visibility = SearchView.GONE
}

fun SearchView.showView() {
    visibility = SearchView.VISIBLE
}