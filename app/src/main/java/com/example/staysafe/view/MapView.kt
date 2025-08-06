package com.example.staysafe.view // Change this to your actual package name

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.MapUiSettings
import com.example.staysafe.viewModel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapView(
    userID: Int,
    viewModel: MapViewModel = viewModel(),
    ctx: Context,
    onActivitiesClicked: (userID: Int) -> Unit,
    onContactsClicked: (userID: Int) -> Unit,
    onSettingsClicked: (userID: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val locationPermissions = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    val currentLocation by viewModel.currentLocation.collectAsStateWithLifecycle()

    var location by remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(currentLocation) {
        viewModel.getCurrentLocation(ctx)
        location = currentLocation
    }
    val contactActivities by viewModel.contactActivities.collectAsStateWithLifecycle()

    LaunchedEffect(userID) {
        viewModel.getContactActivities(userID)
    }

    Log.d("MapView", "ViewModel Location: $currentLocation")

    viewModel.getUser(userID)

    Log.d("MapView", "Starting Location: $location")

    val currentUser = viewModel.currentUser

    // Set the initial camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(location?.latitude ?: 51.51, location?.longitude ?: -0.13),
            location?.let { 20f } ?: 10f
        )
    }

    Log.d("MapView", "User: ${currentUser.value}")

    Scaffold(
        bottomBar = {
            NavigationBar() {
                // Contacts
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Contacts") },
                    label = { Text("Contacts") },
                    onClick = { onContactsClicked(userID) },
                    selected = false
                )
                // Map
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Map") },
                    label = { Text("Map") },
                    onClick = { /* Handle Home click */ },
                    selected = true
                )
                // Activities
                NavigationBarItem(
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Outlined.DirectionsWalk,
                            contentDescription = "Activities"
                        )
                    },
                    label = { Text("Activities") },
                    onClick = { onActivitiesClicked(userID) },
                    selected = false
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    onClick = { onSettingsClicked(userID) },
                    selected = false
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            // Map View
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                ),
                properties = MapProperties(isMyLocationEnabled = true)
            )
        }
    }
}