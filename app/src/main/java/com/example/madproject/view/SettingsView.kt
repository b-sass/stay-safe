package com.example.madproject.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.madproject.viewModel.UserViewModel

@Composable
fun SettingsView(UserID: Int, navController: NavController, viewModel: UserViewModel = viewModel()) {
    val user = viewModel.currentUser.value

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
        UserInfoRow(label = "First Name:", value = viewModel.getUser(UserID).toString())
        UserInfoRow(label = "First Name:", value = user?.firstName ?: "N/A")
        UserInfoRow(label = "Last Name:", value = user?.lastName ?: "N/A")
        UserInfoRow(label = "Email:", value = user?.userName ?: "N/A")
        UserInfoRow(label = "Password:", value = user?.password ?: "N/A")

        // Back Button
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.Edit, contentDescription = "Back")
        }

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