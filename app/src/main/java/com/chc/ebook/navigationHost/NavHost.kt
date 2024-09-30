package com.chc.ebook.navigationHost

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chc.ebook.ui.screen.content.ContentPage
import com.chc.ebook.ui.screen.home.HomePage
import com.chc.ebook.utils.LocalNavController

@Composable
fun NavHostComp(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val enterTransition = remember {
        slideInVertically(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            initialOffsetY = { it / 2 }
        ) + fadeIn(
            animationSpec = tween(
                durationMillis = 600
            )
        )
    }
    val exitTransition = remember {
        slideOutVertically(
            animationSpec = tween(
                durationMillis = 300,
                easing = FastOutLinearInEasing
            ),
            targetOffsetY = { it }
        ) + fadeOut(
            animationSpec = tween(
                durationMillis = 200
            )
        )
    }

    NavHost(
        modifier = modifier.fillMaxSize(),
        navController = navController,
        startDestination = HOMEPAGE,
    ) {
        composable(HOMEPAGE) {
            HomePage()
        }

        composable(
            CONTENTPAGE,
            enterTransition = { enterTransition },
            popEnterTransition = null,
            popExitTransition = { exitTransition }
        ) {
            ContentPage()
        }
    }
}