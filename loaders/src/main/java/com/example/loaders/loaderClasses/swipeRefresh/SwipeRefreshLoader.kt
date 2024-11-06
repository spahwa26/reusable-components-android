package com.example.loaders.loaderClasses.swipeRefresh

import androidx.compose.runtime.*
import com.example.loaderClasses.swipeRefresh.LottieRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SwipeRefreshLoader(
    animation: Int,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = true),
        onRefresh = {
            //Refresh
        },
        indicator = { state, trigger ->
            LottieRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                animation = animation
            )
        },
    ) {
        //Content
    }
}