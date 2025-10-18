package com.example.staysafe.dialogs

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.staysafe.data.models.Activity
import com.example.staysafe.data.models.Location
import com.example.staysafe.viewModel.MapViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityDialog(
    userID: Int,
    viewModel: MapViewModel = viewModel(),
    onDismissRequest: () -> Unit
) {
    var from by remember { mutableStateOf<Location?>(null) }
    var to by remember { mutableStateOf<Location?>(null) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var expandedFrom by remember { mutableStateOf(false) }
    var expandedTo by remember { mutableStateOf(false) }

    val locations = viewModel.userLocations.collectAsStateWithLifecycle()

    LaunchedEffect(locations) {
        viewModel.getUserLocations(userID)
    }

    MessageDialog(
        onDismissRequest = onDismissRequest,
        header = "New Activity",
        onOkButtonClicked = {
            if (from != null && to != null && name.isNotEmpty()) {
                val activity = Activity(
                    userID = userID,
                    name = name,
                    description = description
                )
                viewModel.createActivity(activity, from!!, to!!)
                onDismissRequest()
            }
        },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Activity Name")}
            )

            ExposedDropdownMenuBox(
                expanded = expandedFrom,
                onExpandedChange = { expandedFrom = !expandedFrom }
            ) {

                OutlinedTextField(
                    value = from?.name ?: "",
                    onValueChange = { from?.name = it },
                    label = { Text("From") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFrom) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                )

                ExposedDropdownMenu(
                    expanded = expandedFrom,
                    onDismissRequest = { expandedFrom = false }
                ) {

                    for (location in locations.value) {
                        DropdownMenuItem(
                            text = { Text(location.name) },
                            onClick = { from = location; expandedFrom = false }
                        )
                    }
                }
            }

            ExposedDropdownMenuBox(
                expanded = expandedTo,
                onExpandedChange = { expandedTo = !expandedTo }
            ) {

                OutlinedTextField(
                    value = to?.name ?: "",
                    onValueChange = { to?.name = it },
                    label = { Text("To") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTo) },
                    modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable, true)
                )

                ExposedDropdownMenu(
                    expanded = expandedTo,
                    onDismissRequest = { expandedTo = false }
                ) {
                    for (location in locations.value) {
                        DropdownMenuItem(
                            text = { Text(location.name) },
                            onClick = { to = location; expandedTo = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Activity Description (optional)") }
            )
        }
    }
}

