package com.example.loaders.loaderClasses


import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.razzaghi.compose_loading_dots.LoadingCircleFady
import com.razzaghi.compose_loading_dots.core.rememberDotsLoadingController

@Composable
fun CircularDotsLoader(
    selectedDotsCountTill8: Int = 3,
    dotsColor: Color = MaterialTheme.colors.primary,
    dotsSize: Dp = 15.dp,
    duration: Int = 1000,
    easing: Easing = LinearEasing,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val rememberLoadingCircleFadyController = rememberDotsLoadingController()

        LoadingCircleFady(
            controller = rememberLoadingCircleFadyController,
            modifier = Modifier.padding(vertical = 8.dp),
            dotsCount = selectedDotsCountTill8,
            dotsColor = dotsColor,
            dotsSize = dotsSize,
            duration = duration,
            easing = easing
        )
    }
}