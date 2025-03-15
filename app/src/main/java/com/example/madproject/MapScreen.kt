package com.example.madproject // Change this to your actual package name

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen() {
    // Set the initial camera position
    val london = LatLng(51.5074, -0.1278)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(london, 10f) // Initial position (London)
    }

    // Create the Google Map
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    )
}