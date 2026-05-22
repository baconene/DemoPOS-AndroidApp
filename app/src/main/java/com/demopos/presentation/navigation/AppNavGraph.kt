package com.demopos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demopos.presentation.ui.screens.SplashScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(navController)
        }
        // More routes will be added in subsequent phases
    }
}
