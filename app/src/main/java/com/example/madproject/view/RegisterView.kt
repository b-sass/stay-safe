package com.example.madproject.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import com.example.madproject.dialogs.MessageDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun RegisterView(
    ctx: Context,
    onDismissRequest: () -> Unit = {},
) {
    val api = ApiRepository()
    val scope = rememberCoroutineScope()
    val locationProvider = LocationServices.getFusedLocationProviderClient(ctx)

    val permissions = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    var firstName by remember { mutableStateOf("firstname") }
    var lastName by remember { mutableStateOf("lastname") }
    var username by remember { mutableStateOf("niceusername") }
    var password by remember { mutableStateOf("password123") }
    var phoneNumber by remember { mutableStateOf("+447123456789") }

    var messageDialog by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }

    if (messageDialog) {
        MessageDialog(
            message = message,
            onDismissRequest = { messageDialog = false; onDismissRequest() }
        )
    }

    Scaffold(
        topBar = { TopAppBar(
            title = { "Create user" },
            navigationIcon = {
                // Back
                IconButton(
                    onClick = { onDismissRequest() }
                ) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back arrow") }
            },
            actions = {
                Text(
                    text = "Create",
                    modifier = Modifier.padding(end = 8.dp)
                        .clickable(onClick = {
                            var lat = 0.0
                            var lon = 0.0

                            if (ctx.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                locationProvider.lastLocation.addOnSuccessListener { location: Location? ->
                                    val newUser = User(
                                        firstName = firstName,
                                        lastName = lastName,
                                        userName = username,
                                        phone = phoneNumber,
                                        password = password,
                                        latitude = lat,
                                        longitude = lon
                                    )

                                    scope.launch {
                                        api.createUser(newUser)
                                    }

                                    message = "User created successfully."
                                    messageDialog = true
                                }
                            }
                        }
                    )
                )
            }
        )}
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(innerPadding).fillMaxWidth()
        ) {
            Row {
                // First Name
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") }
                )

                // Last Name
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") }
                )
            }

            // Username
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )

            // Password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }
            )
            // Phone number
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone number") }
            )
        }
    }

}