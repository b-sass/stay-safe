package com.example.madproject.navigation

import android.util.Log.d
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.madproject.view.ActivityView
import com.example.madproject.view.ContactView
import com.example.madproject.view.MapView
import kotlinx.serialization.Serializable
import com.example.madproject.view.data
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MapView
    ) {
        composable<MapView> { MapView(
            onContactsClicked = {navController.navigate(ContactView)}
        ) }
        composable<ContactView> { ContactView() }
        composable <ActivityView> { ActivityView() }
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