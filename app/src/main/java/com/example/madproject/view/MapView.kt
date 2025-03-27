package com.example.madproject.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationProvider
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.MapUiSettings
import com.example.madproject.viewModel.MapViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.LocationSource
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapView(
    userID: Int,
    viewModel: MapViewModel = viewModel(),
    ctx: Context,
    onContactsClicked: () -> Unit,
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

    Log.d("MapView", "Starting Location: $location")

    val currentUser = viewModel.currentUser

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
                    icon = { Icon(Icons.Outlined.Person, "Contacts") },
                    label = { Text("Contacts") },
                    onClick = { onContactsClicked() },
                    selected = false
                )
                // Map
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.LocationOn, "Map") },
                    label = { Text("Map") },
                    onClick = { /* Handle Home click */ },
                    selected = true
                )
                // Settings
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Outlined.DirectionsWalk, "Activities") },
                    label = { Text("Activities") },
                    onClick = { showBottomSheet = true },
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
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
            ) {
                Column {
                    Text("$location")
                }
            }
        }
    }
}