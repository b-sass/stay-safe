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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.staysafe.data.models.Location
import com.example.staysafe.viewModel.ContactViewModel
import com.example.staysafe.viewModel.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactDialog(
    viewModel: ContactViewModel = viewModel(),
    onDismissRequest: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var label by remember { mutableStateOf("") }

    MessageDialog(
        onDismissRequest = onDismissRequest,
        header = "New Place",
        onOkButtonClicked = {
            if (username.isEmpty() || label.isEmpty()) {
                Log.e("AddContactDialog", "Please fill in all fields")
            } else {
                viewModel.createContact(
                    """
                    {
                        "user": ${viewModel.userID},
                        "contact": "$username",
                        "label": "$label"
                    }
                    """.trimIndent()
                )
            }
            onDismissRequest()
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Contact username")}
            )

            OutlinedTextField(
                value = label,
                onValueChange = { label = it },
                label = { Text("Contact Label") }
            )
        }
    }
}

