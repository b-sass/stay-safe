package com.example.madproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madproject.view.MapView
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MapView
    ) {
        composable<MapView> { MapView() }
    }
}

@Serializable
object MapView
@Serializable
object LoginView