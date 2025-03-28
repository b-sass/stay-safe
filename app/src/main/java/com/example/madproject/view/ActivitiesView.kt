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
import com.example.madproject.data.models.Activity
import com.example.madproject.viewModel.ActivityViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date

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
fun ActivityView(
    userID: Int,
    viewModel: ActivityViewModel = viewModel(),
) {
    viewModel.userID = userID
    viewModel.getUser()
    var activities = viewModel.activities.collectAsStateWithLifecycle()
    viewModel.getActivities(userID)

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
            items(activities.value ?: emptyList()) { activity ->
                ActivityCard(
                    activity = activity,
                    onDelete = {
                        viewModel.deleteActivity(activity.id!!)
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

//        if (isEditing && currentActivity != null) {
//            EditActivityDialog(
//                activity = currentActivity!!,
//                onDismiss = { isEditing = false },
//                onUpdate = { updatedActivity ->
//                    val index = activities.value?.indexOfFirst { it.id == updatedActivity.id }
//                    if (index != -1) {
//                        activities[index] = updatedActivity
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar("Activity updated: ${updatedActivity.name}")
//                        }
//                    } else {
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar("Error: Activity not found.")
//                        }
//                    }
//                    isEditing = false
//                }
//            )
//        }

        if (showAdd) {
            AddActivityDialog(
                onDismiss = { showAdd = false },
                onAdd = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Activity added")
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
    viewModel: ActivityViewModel = viewModel(),
    onDismiss: () -> Unit,
    onAdd: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var userName by remember { mutableStateOf("") }
    var startName by remember { mutableStateOf("") }
    var endName by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Planned") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Activity") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                TextField(value = startDate.toString(), onValueChange = { startDate = it }, label = { Text("Start Date") })
                TextField(value = endDate.toString(), onValueChange = { endDate = it }, label = { Text("End Date") })
                TextField(value = userName, onValueChange = { userName = it }, label = { Text("Username") })
                TextField(value = startName, onValueChange = { startName = it }, label = { Text("Start Name") })
                TextField(value = endName, onValueChange = { endName = it }, label = { Text("End Name") })
                TextField(value = status, onValueChange = { status = it }, label = { Text("Status") })
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.createActivity(Activity(
                    name = name,
                    userID = viewModel.userID!!,
                    description = description,
                    startDate = LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME),
                    startLocationID = 0,
                    endDate = LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME),
                    endLocationID = 0,
                    userName = viewModel.user.value?.userName!!,
                    startName = startName,
                    endName = endName,
                    status = status
                ))
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