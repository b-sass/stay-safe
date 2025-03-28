package com.example.madproject.viewModel

import android.location.Location
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
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