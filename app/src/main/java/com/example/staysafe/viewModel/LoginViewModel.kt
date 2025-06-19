package com.example.staysafe.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.User
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val api = ApiRepository()
    var users: List<User> = mutableStateListOf<User>()
    var loggedInUser: User? = null

    fun getUsers() {
        viewModelScope.launch {
            users = api.getUsers()
        }
    }

    fun getUserWithCredentials(userName: String, password: String) {
        loggedInUser = users.find { it.userName == userName && it.password == password }
    }
}