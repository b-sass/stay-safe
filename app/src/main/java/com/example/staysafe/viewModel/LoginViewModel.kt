package com.example.staysafe.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.User
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    val api = ApiRepository()
    var loggedInUser: User? = null

    fun getUserWithCredentials(username: String, password: String) {
        viewModelScope.launch {
            loggedInUser = api.loginUser(username, password)
        }
    }
}