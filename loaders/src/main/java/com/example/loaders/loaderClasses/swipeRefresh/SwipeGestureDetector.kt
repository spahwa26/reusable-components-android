package com.example.loaders.loaderClasses.swipeRefresh

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.compose.ui.platform.ComposeView
import com.example.loaders.utils.LoaderVisibility

fun ComposeView.startRefreshing(context: Context, animation: Int): GestureDetector {
    val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                if (distanceY < 0) {
                    setContent {
                        LoaderVisibility(true) {
                            SwipeRefreshLoader(animation = animation)
                        }
                    }
                    return true
                }
                return false
            }
        })
    return gestureDetector
}

fun ComposeView.stopRefreshing(animation: Int) {
    setContent {
        LoaderVisibility(false) {
            SwipeRefreshLoader(animation = animation)
        }
    }
}



