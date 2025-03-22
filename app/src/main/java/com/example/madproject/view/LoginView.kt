package com.example.madproject.view

import android.app.Activity
import android.content.Intent
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.dialogs.UserLoginDialog
import com.example.madproject.viewModel.LoginViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LoginView(
    viewModel: LoginViewModel = viewModel(),
    onLogin: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val locationPermissions = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    var userPicker by remember { mutableStateOf(false) }


    if (userPicker) {
        UserLoginDialog(
            onDismissRequest = { userPicker = false },
        )
    }

    if (locationPermissions.allPermissionsGranted) {
        Scaffold() { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding)
                    .fillMaxSize()
            ) {
                Text("Choose Account")
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = { onRegisterButtonClicked() }) { Text("Register")}
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = { userPicker = true }) { Text("Choose User")}
                Spacer(modifier = Modifier.height(4.dp))
                Button( onClick = { onLogin() } ) { Text("Login") }
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

