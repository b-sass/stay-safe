package com.example.madproject.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.madproject.data.models.Contact

// Function to get a list of contacts
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
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(8.dp)
    ) {
        items(data()) { contact -> // Ensure getContacts() returns a List<Contact>
            ContactCard(
                contact = contact,
                onDelete = { /* Handle delete action */ },
                onEdit = { /* Handle edit action */ },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}