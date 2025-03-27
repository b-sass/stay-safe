package com.example.madproject.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.viewModel.LoginViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginView(
    viewModel: LoginViewModel = viewModel(),
    onLogin: (userID: Int) -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    viewModel.getUsers()

    var username by remember { mutableStateOf("niceusername") }
    var password by remember { mutableStateOf("password123") }

    val locationPermissions = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    if (locationPermissions.allPermissionsGranted) {
        Scaffold() { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
            ) {
                Text("Stay Safe")
                Text("Log in to your account")
                Spacer(modifier = Modifier.height(4.dp))
                // Login inputs
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(24.dp)
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                    )
                }
                // Buttons
                Button( onClick = {
                    try {
                        viewModel.getUserWithCredentials(username, password)
                        onLogin(viewModel.loggedInUser?.id!!)
                    } catch (e: NullPointerException) {
                        Log.d("LoginView", "Can't find user\nusername: ${viewModel.loggedInUser}")
                    }
                } ) { Text("Login") }
                Spacer(modifier = Modifier.height(4.dp))
                Text("Don't have an account?")
                Button(onClick = { onRegisterButtonClicked() }) { Text("Register")}
            }
        }
    } else {
        LaunchedEffect(locationPermissions) {
            locationPermissions.launchMultiplePermissionRequest()
        }

        Scaffold() { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize(),
            ) {
                Text("Location permissions are required to use this app")
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { locationPermissions.launchMultiplePermissionRequest() }
                ) {
                    Text("Request Location Permissions")
                }
            }
        }
    }
}

