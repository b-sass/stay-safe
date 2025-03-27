package com.example.madproject.navigation

import android.util.Log.d
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madproject.view.*
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MapView
    ) {
        composable<Map> { backStackEntry ->
            val args = backStackEntry.toRoute<Map>()
            MapView(
                userID = args.userID,
                ctx = ctx,
                onContactsClicked = { navController.navigate(Contact) }
            )
        }
        composable<Login> { LoginView(
            onLogin = { navController.navigate(Map(it)) },
            onRegisterButtonClicked = { navController.navigate(Register) }
        ) }
        composable<Register> { RegisterView(
            ctx = ctx,
            onDismissRequest = { navController.popBackStack() },
        ) }
        composable<ContactView> { Contact() }
        composable <ActivityView> { Activity() }
    }
}

@Serializable
object Map
@Serializable
object Login
@Serializable
object Contact
@Serializable
object Activity