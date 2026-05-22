package com.demopos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.demopos.presentation.ui.screens.DashboardScreen
import com.demopos.presentation.ui.screens.LoginScreen
import com.demopos.presentation.ui.screens.PinLoginScreen
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
        
        composable("login") {
            LoginScreen(navController)
        }
        
        composable("pin_login") {
            PinLoginScreen(navController)
        }
        
        composable("dashboard") {
            DashboardScreen(navController)
        }
        
        // More routes will be added in subsequent phases
        // "products", "pos_checkout", "inventory", "reports", etc.
    }
}
