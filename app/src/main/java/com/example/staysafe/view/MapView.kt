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
        viewModel.getUserContacts(userID)
        viewModel.getContactActivities(userID)
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
            ) {
                // Plot started activities from contacts
                contactActivities.forEach { activity ->
                    // Get coordinates from Activities API
                    val startLocationId = activity.startLocationID
                    val startLocationData =
                        remember { mutableStateOf<com.example.madproject.data.models.Location?>(null) }

                    // Get end location data
                    val endLocationId = activity.endLocationID
                    val endLocationData =
                        remember { mutableStateOf<com.example.madproject.data.models.Location?>(null) }

                    val routePoints = remember { mutableStateOf(emptyList<LatLng>()) }

                    LaunchedEffect(activity.id) {
                        val startLoc = viewModel.fetchLocation(activity.startLocationID)
                        val endLoc = viewModel.fetchLocation(activity.endLocationID)

                        // Update your marker states
                        startLocationData.value = startLoc
                        endLocationData.value = endLoc

                        val startCoord =
                            startLocationData.value?.let { LatLng(it.latitude, it.longitude) }
                        val endCoord =
                            endLocationData.value?.let { LatLng(it.latitude, it.longitude) }

                        if (startCoord != null && endCoord != null) {
                            viewModel.getRouteMatrix(startCoord, endCoord) { result ->
                                result.onSuccess { matrix ->
                                    routePoints.value = matrix.polyline?.let {
                                        decodePolyline(it)
                                    } ?: emptyList()
                                }
                                result.onFailure {
                                    Log.e("MapView", "Failed to get route: ${it.message}")
                                    routePoints.value = emptyList()
                                }
                            }
                        }
                    }

                    // If we have location data, add marker
                    startLocationData.value?.let { location ->
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                            ),
                            title = "${activity.userName}'s Activity: ${activity.name}",
                            snippet = "Start: ${activity.status}"
                        )
                    }

                    endLocationData.value?.let { location ->
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    location.latitude,
                                    location.longitude
                                )
                            ),
                            title = "${activity.userName}'s Activity: ${activity.name}",
                            snippet = "END: ${activity.endName}"
                        )
                    }
                }
            }
            if (contactActivities.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Contact Activities",
                            style = MaterialTheme.typography.titleMedium
                        )
                        contactActivities.take(3).forEach { activity ->
                            Text(
                                "${activity.userName}: ${activity.name}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun decodePolyline(encoded: String): List<LatLng> {
    val poly = mutableListOf<LatLng>()
    var index = 0
    var lat = 0
    var lng = 0
    val len = encoded.length

    while (index < len) {
        // Decode latitude
        var b: Int
        var shift = 0
        var result = 0
        do {
            b = encoded[index++].toInt() - 63
            result = result or ((b and 0x1f) shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlat = if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)
        lat += dlat

        // Decode longitude
        shift = 0
        result = 0
        do {
            b = encoded[index++].toInt() - 63
            result = result or ((b and 0x1f) shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlng = if ((result and 1) != 0) (result shr 1).inv() else (result shr 1)
        lng += dlng

        poly.add(LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5))
    }
    return poly
}
