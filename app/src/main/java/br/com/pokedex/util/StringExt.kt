package br.com.pokedex.util

private const val TWO_ZERO_FORMAT = "#00"
private const val ONE_ZERO_FORMAT = "#0"
private const val ZERO_ZERO_FORMAT = "#"

fun emptyString() = ""

fun String.toIdForm() =
    if (this.toInt() < 10) {
        "$TWO_ZERO_FORMAT$this"
    } else if(this.toInt() < 100) {
        "$ONE_ZERO_FORMAT$this"
    } else {
        "$ZERO_ZERO_FORMAT$this"
    }