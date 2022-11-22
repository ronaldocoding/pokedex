package br.com.pokedex.util

import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.hideView() {
    visibility = ShimmerFrameLayout.GONE
}

fun ShimmerFrameLayout.showView() {
    visibility = ShimmerFrameLayout.VISIBLE
}