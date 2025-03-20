package com.example.madproject.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.viewModel.LoginViewModel

@Composable
fun LoginView(
    viewModel: LoginViewModel = viewModel(),
    onLogin: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Scaffold() { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
        ) {
            Text("Choose Account")
            Box() {
                Button(
                    onClick = { expanded = true }
                ) { Text("Select") }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("User") },
                        onClick = { expanded = false }
                    )
                }
            }
            Button(
                onClick = { onLogin() }
            ) {
                Text("Login")
            }
        }
    }
}
