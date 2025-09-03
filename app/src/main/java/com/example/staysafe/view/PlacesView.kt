package com.example.staysafe.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.staysafe.data.models.Location

val place1 = Location(1, "Home", 52.24774, 21.01468)
val place2 = Location(2, "Uni", 52.24020, 21.01843)
val place3 = Location(3, "Shops", 52.23002, 21.00272)
val place4 = Location(4, "Work", 52.24313, 21.01661)

var places: List<Location>? = listOf(place1, place2, place3, place4)

@Composable
fun PlacesView() {
    Scaffold { innerPadding ->
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