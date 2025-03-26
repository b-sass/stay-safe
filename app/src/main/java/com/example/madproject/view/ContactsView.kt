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
import com.example.madproject.data.models.Contact
import kotlinx.coroutines.launch

// Assuming the Contact class has a dateCreated property


fun data(): List<Contact> {
    return listOf(
        Contact(id = 1, userID = "user 1", contactID = "contact 1", label = "John Doe"),
        Contact(id = 2, userID = "user 2", contactID = "contact 2", label = "Jane Smith"),
        Contact(id = 3, userID = "user 3", contactID = "contact 3", label = "Alice Johnson"),
        Contact(id = 4, userID = "user 4", contactID = "contact 4", label = "Bob Brown"),
        Contact(id = 5, userID = "user 5", contactID = "contact 5", label = "Charlie White")
    )
}

@Composable
fun ContactCard(
    contact: Contact,
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
            Text(text = "User  ID: ${contact.userID}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Contact ID: ${contact.contactID}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Date Created: ${contact.dateCreated}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Contact")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Contact")
                }
            }
        }
    }
}

@Composable
fun ContactView() {
    val contacts = remember { mutableStateListOf(*data().toTypedArray()) }
    var isEditing by remember { mutableStateOf(false) }
    var currentContact by remember { mutableStateOf<Contact?>(null) }
    var showAdd by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(contacts) { contact ->
                ContactCard(
                    contact = contact,
                    onDelete = {
                        contacts.remove(contact)
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


        if (isEditing && currentContact != null) {
            EditContactDialog(
                contact = currentContact!!,
                onDismiss = { isEditing = false },
                onUpdate = { updatedContact ->
                    val index = contacts.indexOfFirst { it.id == updatedContact.id }
                    if (index != -1) {
                        contacts[index] = updatedContact
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Contact updated: ${updatedContact.label}")
                        }
                    }
                    isEditing = false
                }
            )
        }


        if (showAdd) {
            AddContactButton(
                onDismiss = { showAdd = false },
                onAdd = { newContact ->
                    contacts.add(newContact)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Contact added: ${newContact.label}")
                    }
                    showAdd = false
                }
            )
        }


        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun EditContactDialog(
    contact: Contact,
    onDismiss: () -> Unit,
    onUpdate: (Contact) -> Unit
) {
    var name by remember { mutableStateOf(contact.label) }
    var userId by remember { mutableStateOf(contact.userID) }
    var contactId by remember { mutableStateOf(contact.contactID) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Contact") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = userId, onValueChange = { userId = it }, label = { Text("User  ID") })
                TextField(value = contactId, onValueChange = { contactId = it }, label = { Text("Contact ID") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onUpdate(contact.copy(label = name, userID = userId, contactID = contactId))
            }) {
                Text("Update")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AddContactButton(
    onDismiss: () -> Unit,
    onAdd: (Contact) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var contactId by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Contact") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = userId, onValueChange = { userId = it }, label = { Text("User  ID") })
                TextField(value = contactId, onValueChange = { contactId = it }, label = { Text("Contact ID") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(Contact(0, name, userId, contactId))
                onDismiss()
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