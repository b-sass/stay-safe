package com.example.madproject.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madproject.view.*
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation(ctx: Context) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginView
    ) {
        composable<MapView> { MapView() }
        composable<LoginView> { LoginView(
            onLogin = { navController.navigate(MapView) },
            onRegisterButtonClicked = { navController.navigate(RegisterView) }
        ) }
        composable<RegisterView> { RegisterView(
            ctx = ctx,
            onDismissRequest = { navController.popBackStack() },
        ) }
    }
}

@Serializable
object MapView
@Serializable
object LoginView
@Serializable
object RegisterView