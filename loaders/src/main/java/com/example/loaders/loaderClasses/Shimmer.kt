package com.example.loaders.loaderClasses

import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.shimmerStart(
    duration: Long = 1500,
    baseAlpha: Float = 0.5f,
    highlightAlpha: Float = 0.9f,
    shimmerDirection: Int = Shimmer.Direction.LEFT_TO_RIGHT
) {
    val shimmer = Shimmer.AlphaHighlightBuilder()
        .setDuration(duration)
        .setBaseAlpha(baseAlpha)
        .setHighlightAlpha(highlightAlpha)
        .setDirection(shimmerDirection)
        .build()

    this.setShimmer(shimmer)
    this.startShimmer()
}

fun ShimmerFrameLayout.shimmerStop() {
    this.hideShimmer()
}