package com.puadevs.leetcoach.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.puadevs.leetcoach.navigation.graphs.rootGraph
import com.puadevs.leetcoach.navigation.routes.StartRoute

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = StartRoute
    ) {
        rootGraph(navController)
    }
}