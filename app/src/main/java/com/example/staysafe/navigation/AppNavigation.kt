package com.example.staysafe.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.staysafe.view.*
import kotlinx.serialization.Serializable

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
                onContactsClicked = { navController.navigate(Contact(it)) },
                onActivitiesClicked = { navController.navigate(Activity) },
                onSettingsClicked = { navController.navigate(Settings) }
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
        composable<Contact> { backStackEntry ->
            val args = backStackEntry.toRoute<Contact>()
            ContactView(args.userID)
        }
        composable <Activity> { ActivityView() }
        composable<Settings> {
            SettingsView(UserID = 0, navController = navController)
        }
    }
}

@Serializable
data class Map(
    val userID: Int
)

@Serializable
object Login
@Serializable
data class Contact (
    val userID: Int
)
@Serializable
object Activity
@Serializable
object Register
@Serializable
object Settings