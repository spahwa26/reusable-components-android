package com.example.loaders.loaderClasses

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun HorizontalProgressBarWithPercent(
    color: Color = MaterialTheme.colors.primary,
    height: Dp = 8.dp,
) {
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            progress += 0.1f
            if (progress > 1f) progress = 0f
        }
    }

    Box(contentAlignment = Alignment.Center) {

        LinearProgressIndicator(
            progress = progress,
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                .height(height)
        )

        Text(
            text = "${(progress * 100).toInt()}%",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
        )
    }
}