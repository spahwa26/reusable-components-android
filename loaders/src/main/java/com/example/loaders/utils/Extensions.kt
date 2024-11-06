package com.example.loaders.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView

@Composable
fun ComposeView.LoaderVisibility(visible: Boolean, myParam: @Composable () -> Unit) {
    this.apply {
        if (visible) {
            setContent {
                MaterialTheme {
                    myParam()
                }
            }
        } else {
            setContent {
                MaterialTheme {
                }
            }
        }
    }
}