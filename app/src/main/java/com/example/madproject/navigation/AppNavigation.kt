package com.example.madproject.navigation

import android.content.Context
import android.util.Log.d
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.madproject.view.*
import kotlinx.serialization.Serializable
import com.example.madproject.view.data

@Composable
fun AppNavigation(ctx: Context) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
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
        composable<Contact> { ContactView() }
    }
}

@Serializable
data class Map (
    val userID: Int
)
@Serializable
object Login
@Serializable
object Register
@Serializable
object Contact
