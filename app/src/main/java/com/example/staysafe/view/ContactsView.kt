package com.example.staysafe.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Flag
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.staysafe.data.models.UserContact
import com.example.staysafe.dialogs.AddContactDialog
import com.example.staysafe.dialogs.AddPlaceDialog
import com.example.staysafe.viewModel.ContactViewModel

private val viewModel = ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsView(
    userID: Int,
    onMapClicked: (userID: Int) -> Unit,
    onPlacesClicked: (userID: Int) -> Unit,
    onSettingsClicked: (userID: Int) -> Unit,
) {
    viewModel.userID = userID
    val contacts = viewModel.contacts.collectAsStateWithLifecycle()

    LaunchedEffect(contacts) {
        viewModel.getUserContacts()
    }

    var showAddContactDialog by remember { mutableStateOf(false) }

    if (showAddContactDialog) {
        AddContactDialog(
            onDismissRequest = { showAddContactDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Contacts") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddContactDialog = true }
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
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Contacts") },
                    label = { Text("Contacts") },
                    onClick = { },
                    selected = true
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
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
//            TODO: Implement Favourites
//            Favourites()
//            HorizontalDivider()
            Contacts(contacts.value)
        }
    }
}

@Composable
fun FavouriteContacts() {
    throw NotImplementedError("Favourites not implemented yet")
}

@Composable
fun Contacts(contacts: List<UserContact>) {
    if (contacts.isEmpty()) {
        Text("No contacts saved")
        return
    }

    LazyColumn(
        modifier = Modifier.padding(8.dp).selectableGroup()
    ) {
        items(contacts.size) { contact ->
            ContactCard(contacts[contact])
        }
    }
}

@Composable
fun ContactCard(contact: UserContact) {
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
                Text(text = contact.label, fontWeight = FontWeight.Bold)

                Text(text = "${contact.username} | ${contact.latitude}, ${contact.longitude}", fontWeight = FontWeight.Thin,  fontStyle = FontStyle.Italic)
            }
            IconButton(
                onClick = {
                    viewModel.deleteContact(contact.id!!)
                }
            ) {
                Icon(Icons.Filled.Delete, "Delete Place Button")
            }
        }
    }
}

