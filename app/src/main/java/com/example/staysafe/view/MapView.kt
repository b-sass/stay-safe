package com.example.staysafe.view

import android.Manifest
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsRun
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.staysafe.dialogs.ActivityDialog
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.MapUiSettings
import com.example.staysafe.viewModel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.maps.android.compose.MapProperties

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapView(
    userID: Int,
    viewModel: MapViewModel = viewModel(),
    ctx: Context,
    onPlacesClicked: (userID: Int) -> Unit,
    onContactsClicked: (userID: Int) -> Unit,
    onSettingsClicked: (userID: Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val locationPermissions = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    var showActivities by remember { mutableStateOf(false) }

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

    val scaffoldState = rememberBottomSheetScaffoldState()

    if (showActivities) {
        ActivityDialog(userID = userID, onDismissRequest = { showActivities = false })
    }

    Scaffold(
        bottomBar = {
            NavigationBar() {
                // Map
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Map") },
                    label = { Text("Map") },
                    onClick = { /* Handle Home click */ },
                    selected = true
                )
                // Contacts
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Contacts") },
                    label = { Text("Contacts") },
                    onClick = { onContactsClicked(userID) },
                    selected = false
                )
                // Places
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Flag, contentDescription = "Places") },
                    label = { Text("Places") },
                    onClick = { onPlacesClicked(userID) },
                    selected = false
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    onClick = { onSettingsClicked(userID) },
                    selected = false
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showActivities = true },

                ) {
                Icon(Icons.AutoMirrored.Outlined.DirectionsRun, contentDescription = "Activities")
            }
        }
    ) { innerPadding ->
        // Map View
        GoogleMap(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            cameraPositionState = cameraPositionState,
            mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
            ),
            properties = MapProperties(isMyLocationEnabled = true)
        )
    }
}

@Preview
@Composable
fun MapViewPreview() {
    MapView(
        userID = 1,
        ctx = LocalContext.current,
        onPlacesClicked = {},
        onContactsClicked = {},
        onSettingsClicked = {},
    )
}