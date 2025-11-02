package com.puadevs.leetcoach.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.puadevs.leetcoach.features.chat.view.ChatScreen
import com.puadevs.leetcoach.features.start.view.StartScreen
import com.puadevs.leetcoach.navigation.routes.ChatRoute
import com.puadevs.leetcoach.navigation.routes.StartRoute
import com.puadevs.leetcoach.navigation.utils.animatedScreen

fun NavGraphBuilder.rootGraph(
    navController: NavController
) {

    animatedScreen<StartRoute> {
        StartScreen(navController)
    }

    animatedScreen<ChatRoute> {
        ChatScreen()
    }
}