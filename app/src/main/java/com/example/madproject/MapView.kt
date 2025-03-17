package com.example.madproject // Change this to your actual package name

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.DirectionsWalk
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.ComposeMapColorScheme
import com.google.maps.android.compose.MapUiSettings
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapView() {
    // Set the initial camera position
    val coroutineScope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(51.5074, -0.1278),
            10f
        ) // Initial position (London)
    }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = true
    )

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
                )
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
                    LazyColumn {
                        items(10) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Filled.Person, "Contact", modifier = Modifier.size(32.dp))
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text("Contact $it", fontSize = 32.sp)
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}