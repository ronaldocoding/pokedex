package br.com.pokedex.util

import android.widget.TextView

fun TextView.showIf(condition: Boolean) {
    if(condition) {
        visibility = TextView.VISIBLE
    }
}