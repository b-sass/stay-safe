package com.example.staysafe.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.staysafe.data.models.Location

val place1 = Location(1, "Home", 52.24774, 21.01468)
val place2 = Location(2, "Uni", 52.24020, 21.01843)
val place3 = Location(3, "Shops", 52.23002, 21.00272)
val place4 = Location(4, "Work", 52.24313, 21.01661)

var places: List<Location>? = listOf(place1, place2, place3, place4)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesView() {

    var showAddPlaceDialog by remember { mutableStateOf(false) }

    if (showAddPlaceDialog) {
        throw NotImplementedError("Add Place Dialog not implemented yet")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Places") },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddPlaceDialog = true }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add place")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
//            TODO: Implement Favourites
//            Favourites()
//            HorizontalDivider()
            Places()
        }
    }
}

@Composable
fun Favourites() {
    throw NotImplementedError("Favourites not implemented yet")
}

@Composable
fun Places() {
    if (places == null) {
        Text("No places saved")
        return
    }
    for (place in places) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Column() {
                Text(place.name)
                Text("${place.latitude}, ${place.longitude}")
            }
        }
    }
}