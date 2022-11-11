package br.com.pokedex.util

import android.widget.TextView

fun TextView.showIf(condition: Boolean) {
    if(condition) {
        visibility = TextView.VISIBLE
    }
}

fun TextView.showView() {
    visibility = TextView.VISIBLE
}

fun TextView.hideView() {
    visibility = TextView.GONE
}