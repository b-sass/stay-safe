package com.example.madproject.view



import android.R.attr.password
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.data.models.User
import com.example.madproject.viewModel.MapViewModel
import com.example.madproject.viewModel.UserViewModel
@Composable
fun SettingsView(UserID: Int, viewModel: UserViewModel = viewModel()) {
    val user = viewModel.currentUser.value


    Column(){
        Text("Settings")
        Text("User ID: $UserID")
        Text("First Name: ${user?.firstName}")
        Text("Last Name: ${user?.lastName}")
        Text("Email: ${user?.userName}")
        Text("Password: ${user?.password}")
    }
}

