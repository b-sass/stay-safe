package com.example.madproject.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.data.models.Contact
import com.example.madproject.data.models.User
import com.example.madproject.data.models.UserContact
import com.example.madproject.viewModel.ContactViewModel
import kotlinx.coroutines.launch

@Composable
fun ContactView(
    userID: Int,
    viewModel: ContactViewModel = viewModel()
) {
    viewModel.userID = userID
    var contacts = viewModel.contacts.collectAsStateWithLifecycle()

    var isEditing by remember { mutableStateOf(false) }
    var currentContact by remember { mutableStateOf<UserContact?>(null) }
    var showAdd by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    viewModel.getContacts(userID)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(contacts.value ?: emptyList()) { contact ->
                ContactCard(
                    contact = contact,
                    onDelete = {
                        viewModel.deleteContact(contact.contactID)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Contact deleted: ${contact.label}")
                        }
                    },
                    onEdit = {
                        currentContact = contact
                        isEditing = true
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }


        Button(
            onClick = { showAdd = true },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Contact")
        }

        if (showAdd) {
            AddContactButton(
                userID = userID,
                onDismiss = { showAdd = false },
                onAdd = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Contact added")
                    }
                    viewModel.getContacts(userID)
                    showAdd = false
                }
            )
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun ContactCard(
    contact: UserContact,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onEdit),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = contact.label, style = MaterialTheme.typography.titleMedium)
            Text(text = "User  ID: ${contact.id}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Contact ID: ${contact.contactID}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Date Created: ${contact.dateCreated}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Contact")
                }
            }
        }
    }
}

@Composable
fun AddContactButton(
    userID: Int,
    viewModel: ContactViewModel = viewModel(),
    onDismiss: () -> Unit,
    onAdd: () -> Unit,
) {
    var name by remember { mutableStateOf("fartsmella") }
    var label by remember { mutableStateOf("stinky") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Contact") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Contact Username") })
                TextField(value = label, onValueChange = { label = it }, label = { Text("Contact Label") })
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.addContact(name, label)
                onAdd()
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}