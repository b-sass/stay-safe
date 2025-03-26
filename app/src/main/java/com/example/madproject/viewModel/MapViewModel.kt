package com.example.madproject.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class MapViewModel(): ViewModel() {
    var usersData = MutableLiveData<List<User>>()

    fun getUsers() {
        // Get users from the repository
        viewModelScope.launch {
            val users = ApiRepository().getUsers()
            usersData.postValue(users)
        }
    }
}