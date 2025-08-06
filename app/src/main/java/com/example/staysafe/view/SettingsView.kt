package com.example.staysafe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

// Mock User Data
data class User(
    val userId: Int,
    var firstName: String,
    var lastName: String,
    var userName: String,
    var password: String
)

@Composable
fun SettingsView(UserID: Int, navController: NavController) {
    // Hardcoded user data
    var user by remember { mutableStateOf(User(
        userId = UserID,
        firstName = "John",
        lastName = "Doe",
        userName = "john.doe@example.com",
        password = "password123"
    )) }

    // State for showing the edit dialog
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color(0xFFF0F0F0))
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        UserInfoRow(label = "User  ID:", value = user.userId.toString())
        UserInfoRow(label = "First Name:", value = user.firstName)
        UserInfoRow(label = "Last Name:", value = user.lastName)
        UserInfoRow(label = "Email:", value = user.userName)
        UserInfoRow(label = "Password:", value = user.password)

        // Edit Button
        Button(
            onClick = { showDialog = true },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Edit")
        }

        // Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.Edit, contentDescription = "Back")
        }
    }

    // Edit User Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Edit User Information") },
            text = {
                Column {
                    TextField(
                        value = user.firstName,
                        onValueChange = { user = user.copy(firstName = it) },
                        label = { Text("First Name") }
                    )
                    TextField(
                        value = user.lastName,
                        onValueChange = { user = user.copy(lastName = it) },
                        label = { Text("Last Name") }
                    )
                    TextField(
                        value = user.userName,
                        onValueChange = { user = user.copy(userName = it) },
                        label = { Text("Email") }
                    )
                    TextField(
                        value = user.password,
                        onValueChange = { user = user.copy(password = it) },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation()
                    )
                }
            },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Save")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun UserInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}