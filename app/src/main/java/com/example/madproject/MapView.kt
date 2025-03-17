package com.example.madproject // Change this to your actual package name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapView() {
    // Set the initial camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(51.5074, -0.1278), 10f) // Initial position (London)
    }

    Scaffold(
        bottomBar = {
            NavigationBar() {
                // Contacts
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Person, "Contacts") },
                    label = { Text("Contacts") },
                    onClick = { /* Handle Contacts click */ },
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
                    icon = { Icon(Icons.Outlined.Settings, "Settings") },
                    label = { Text("Settings") },
                    onClick = { /* Handle Settings click */ },
                    selected = false
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                mapColorScheme = ComposeMapColorScheme.FOLLOW_SYSTEM,
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                )
            ) {
                // You can add markers or other map elements here
            }
        }
    }
}
