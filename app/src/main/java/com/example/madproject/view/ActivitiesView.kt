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
import com.example.madproject.data.models.Activity
import kotlinx.coroutines.launch
import java.util.Date


fun Activitydata(): List<Activity> {
    return listOf(
        Activity(
            id = 1,
            userID = "user 1",
            name = "Activity 1",
            description = "Description for Activity 1",
            startDate = Date(),
            startLocationID = 1,
            endDate = Date(),
            endLocationID = 2,
            userName = "User  One",
            startName = "Start Location 1",
            endName = "End Location 1",
            status = "Active"
        ),
        Activity(
            id = 2,
            userID = "user 2",
            name = "Activity 2",
            description = "Description for Activity 2",
            startDate = Date(),
            startLocationID = 2,
            endDate = Date(),
            endLocationID = 3,
            userName = "User  Two",
            startName = "Start Location 2",
            endName = "End Location 2",
            status = "Active"
        )
    )
}


@Composable
fun ActivityCard(
    activity: Activity,
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
            Text(text = activity.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "User  ID: ${activity.userID}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Description: ${activity.description}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Start Location ID: ${activity.startLocationID}", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Activity")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Activity")
                }
            }
        }
    }
}

@Composable
fun ActivityView() {
    val activities = remember { mutableStateListOf(*Activitydata().toTypedArray()) }
    var isEditing by remember { mutableStateOf(false) }
    var currentActivity by remember { mutableStateOf<Activity?>(null) }
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
            items(activities) { activity ->
                ActivityCard(
                    activity = activity,
                    onDelete = {
                        activities.remove(activity)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Activity deleted: ${activity.name}")
                        }
                    },
                    onEdit = {
                        currentActivity = activity
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
            Text("Add Activity")
        }

        if (isEditing && currentActivity != null) {
            EditActivityDialog(
                activity = currentActivity!!,
                onDismiss = { isEditing = false },
                onUpdate = { updatedActivity ->
                    val index = activities.indexOfFirst { it.id == updatedActivity.id }
                    if (index != -1) {
                        activities[index] = updatedActivity
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Activity updated: ${updatedActivity.name}")
                        }
                    } else {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Error: Activity not found.")
                        }
                    }
                    isEditing = false
                }
            )
        }

        if (showAdd) {
            AddActivityDialog(
                onDismiss = { showAdd = false },
                onAdd = { newActivity ->
                    activities.add(newActivity)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Activity added: ${newActivity.name}")
                    }
                    showAdd = false
                }
            )
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}

@Composable
fun EditActivityDialog(
    activity: Activity,
    onDismiss: () -> Unit,
    onUpdate: (Activity) -> Unit
) {
    var name by remember { mutableStateOf(activity.name) }
    var userId by remember { mutableStateOf(activity.userID) }
    var description by remember { mutableStateOf(activity.description) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Activity") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = userId, onValueChange = { userId = it }, label = { Text("User  ID") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onUpdate(activity.copy(name = name, userID = userId, description = description))
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
fun AddActivityDialog(
    onDismiss: () -> Unit,
    onAdd: (Activity) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startLocationID by remember { mutableStateOf(0) }
    var endLocationID by remember { mutableStateOf(0) }
    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf(Date()) }
    var userName by remember { mutableStateOf("") }
    var startName by remember { mutableStateOf("") }
    var endName by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Active") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Activity") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = userId, onValueChange = { userId = it }, label = { Text("User  ID") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = startLocationID.toString(), onValueChange = { startLocationID = it.toIntOrNull() ?: 0 }, label = { Text("Start Location ID") })
                TextField(value = endLocationID.toString(), onValueChange = { endLocationID = it.toIntOrNull() ?: 0 }, label = { Text("End Location ID") })

            }
        },
        confirmButton = {
            Button(onClick = {
                onAdd(Activity(
                    id = 0,
                    name = name,
                    userID = userId,
                    description = description,
                    startDate = startDate,
                    startLocationID = startLocationID,
                    endDate = endDate,
                    endLocationID = endLocationID,
                    userName = userName,
                    startName = startName,
                    endName = endName,
                    status = status
                ))
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