package com.example.madproject.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val api = ApiRepository()
    var users: List<User> = mutableStateListOf<User>()

    init {
        getUsers()
    }

    fun getUsers() {
        viewModelScope.launch {
            users = api.getUsers()
        }
    }

    fun getUserWithCredentials(userName: String, password: String): User? {
        return users.find { it.userName == userName && it.password == password }
    }
}