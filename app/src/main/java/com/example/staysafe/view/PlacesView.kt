package com.example.staysafe.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.staysafe.data.models.Location
import com.example.staysafe.dialogs.AddPlaceDialog
import com.example.staysafe.viewModel.PlacesViewModel


private val viewModel = PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesView(
    userID: Int,
    onMapClicked: (userID: Int) -> Unit,
    onContactsClicked: (userID: Int) -> Unit,
    onSettingsClicked: (userID: Int) -> Unit,
) {
    viewModel.userID = userID
    val places = viewModel.locations.collectAsStateWithLifecycle()

    LaunchedEffect(places) {
        viewModel.getUserPlaces()
    }

    var showAddPlaceDialog by remember { mutableStateOf(false) }

    if (showAddPlaceDialog) {
        AddPlaceDialog(
            onDismissRequest = { showAddPlaceDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Places") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddPlaceDialog = true }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add place")
            }
        },
        bottomBar = {
            NavigationBar() {
                // Map
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.LocationOn, contentDescription = "Map") },
                    label = { Text("Map") },
                    onClick = { onMapClicked(userID) },
                    selected = false
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
                    icon = { Icon(Icons.Filled.Flag, contentDescription = "Places") },
                    label = { Text("Places") },
                    onClick = { },
                    selected = true
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    onClick = { onSettingsClicked(userID) },
                    selected = false
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
//            TODO: Implement Favourites
//            FavouritePlaces()
//            HorizontalDivider()
            Places(places.value)
        }
    }
}

@Composable
fun FavouritePlaces() {
    throw NotImplementedError("Favourites not implemented yet")
}

@Composable
fun Places(places: List<Location>) {
    if (places.isEmpty()) {
        Text("No places saved")
        return
    }

    LazyColumn(
        modifier = Modifier.padding(8.dp).selectableGroup()
    ) {
        items(places.size) { place ->
            PlaceCard(places[place])
        }
    }
}

@Composable
fun PlaceCard(place: Location) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = place.name, fontWeight = FontWeight.Bold)

                Text(text = "${place.latitude}, ${place.longitude}", fontWeight = FontWeight.Thin,  fontStyle = FontStyle.Italic)
            }
            IconButton(
                onClick = {
                    viewModel.deletePlace(place.id!!)
                }
            ) {
                Icon(Icons.Filled.Delete, "Delete Place Button")
            }
        }
    }
}
