package com.example.staysafe.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.User
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    private val api: ApiRepository = ApiRepository()
    var currentUser = mutableStateOf<User?>(null)

    fun getUser(userID: Int) {
        viewModelScope.launch {
            val user =api.getUser(userID)
            currentUser.value = user
        }
    }
}