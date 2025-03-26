package com.example.madproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madproject.view.ActivityView
import com.example.madproject.view.ContactView
import com.example.madproject.view.MapView
import kotlinx.serialization.Serializable

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MapView::class.java.name // Use class reference for the start destination
    ) {
        composable<MapView> {
            MapView(
                onContactsClicked = { navController.navigate(ContactView::class.java.name) }, // Navigate to ContactView
                onActivitiesClicked = { navController.navigate(ActivityView::class.java.name) } // Navigate to ActivityView
            )
        }
        composable<ContactView> { ContactView() }
        composable<ActivityView> { ActivityView() }
    }
}

@Serializable
object MapView
@Serializable
object LoginView
@Serializable
object ContactView
@Serializable
object ActivityView