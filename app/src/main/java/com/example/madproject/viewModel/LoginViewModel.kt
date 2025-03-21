package com.example.madproject.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val api = ApiRepository()
    var users: List<User> = mutableStateListOf<User>()
    var selectedUser: User? by mutableStateOf(null)

    init {
        viewModelScope.launch {
            users = api.getUsers()
        }
    }
}