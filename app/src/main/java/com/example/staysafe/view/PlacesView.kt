package com.example.staysafe.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.staysafe.data.models.Location
import com.example.staysafe.dialogs.AddPlaceDialog
import org.intellij.lang.annotations.JdkConstants

val place1 = Location(1, "Home", 52.24774, 21.01468)
val place2 = Location(2, "Uni", 52.24020, 21.01843)
val place3 = Location(3, "Shops", 52.23002, 21.00272)
val place4 = Location(4, "Work", 52.24313, 21.01661)

var places: List<Location>? = listOf(place1, place2, place3, place4)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlacesView(
    userID: Int,
    onMapClicked: (userID: Int) -> Unit,
    onContactsClicked: (userID: Int) -> Unit,
    onSettingsClicked: (userID: Int) -> Unit,
) {

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

    LazyColumn(
        modifier = Modifier.padding(8.dp)
    ) {
        items(places!!.size) { place ->
            PlaceCard(places!![place])
        }
    }
}

@Composable
fun PlaceCard(place: Location) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = place.name, fontWeight = FontWeight.Bold)

            Text(text = "${place.latitude}, ${place.longitude}", fontWeight = FontWeight.Thin,  fontStyle = FontStyle.Italic)
        }
    }
}