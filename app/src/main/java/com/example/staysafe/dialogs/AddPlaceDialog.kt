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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.staysafe.data.models.Location
import com.example.staysafe.viewModel.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlaceDialog(
    onDismissRequest: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var latitude: Double? by remember { mutableStateOf(null) }
    var longitude: Double? by remember { mutableStateOf(null) }

    val viewModel = PlacesViewModel

    MessageDialog(
        onDismissRequest = onDismissRequest,
        header = "New Place",
        onOkButtonClicked = {
            if (name.isEmpty() || latitude == null || longitude == null) {
                Log.e("AddPlaceDialog", "Please fill in all fields")
            } else {
                viewModel.createPlace(
                    Location(
                        name = name,
                        latitude = latitude!!,
                        longitude = longitude!!
                    )
                )
            }
            onDismissRequest()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Place Name")}
            )

            OutlinedTextField(
                value = latitude?.toString() ?: "",
                onValueChange = { latitude = it.toDoubleOrNull() },
                label = { Text("Latitude") }
            )

            OutlinedTextField(
                value = longitude?.toString() ?: "",
                onValueChange = { longitude = it.toDoubleOrNull() },
                label = { Text("Longitude") }
            )
        }
    }
}

