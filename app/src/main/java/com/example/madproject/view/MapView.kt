package com.example.madproject.view // Change this to your actual package name

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.data.models.User
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.MapUiSettings
import com.example.madproject.viewModel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.maps.android.compose.MapProperties

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapView(
    userID: Int,
    viewModel: MapViewModel = viewModel(),
    ctx: Context,
    onActivitiesClicked: () -> Unit,
    onContactsClicked: () -> Unit,
    onSettingsClicked: () -> Unit
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

    Log.d("MapView", "ViewModel Location: $currentLocation")

    viewModel.getUser(userID)
    viewModel.getUserContacts(userID)

    Log.d("MapView", "Starting Location: $location")

    val currentUser = viewModel.currentUser
    val userContacts by viewModel.userContacts.collectAsStateWithLifecycle()

    // Set the initial camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(location?.latitude ?: 51.51 , location?.longitude ?: -0.13),
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
                    onClick = { onContactsClicked() },
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
                    icon = { Icon(Icons.AutoMirrored.Outlined.DirectionsWalk, contentDescription = "Activities") },
                    label = { Text("Activities") },
                    onClick = { onActivitiesClicked() },
                    selected = false
                )
                NavigationBarItem(
                    icon = {Icon(Icons.Filled.Person, contentDescription = "Settings")},
                    label = {Text("Settings")},
                    onClick = { onSettingsClicked() },
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
            ) {
                // You can add markers or other map elements here
            }
        }
    }
}