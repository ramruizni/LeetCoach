package com.puadevs.leetcoach.navigation.utils

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

// Applies standard slide transitions to screen destinations
inline fun <reified T : Any> NavGraphBuilder.animatedScreen(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) + fadeIn() },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) + fadeOut() },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) + fadeIn() },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) + fadeOut() },
        content = content,
    )
}

// Applies bottom slide transitions to screen destinations
inline fun <reified T : Any> NavGraphBuilder.bottomAnimatedScreen(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = { slideInVertically(initialOffsetY = { it }) + fadeIn() },
        exitTransition = { slideOutVertically(targetOffsetY = { -it }) + fadeOut() },
        popEnterTransition = { slideInVertically(initialOffsetY = { -it }) + fadeIn() },
        popExitTransition = { slideOutVertically(targetOffsetY = { it }) + fadeOut() },
        content = content,
    )
}
