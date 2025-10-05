package com.example.staysafe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.User
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    val api = ApiRepository()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun getUser(userID: Int) {
        viewModelScope.launch {
            _user.value = api.getUser(userID)
        }
    }

    fun updateUsername(userID: Int, username: String) {
        viewModelScope.launch {
            try {
                api.updateUser(
                    userID, """
                    {
                        "username": "$username"
                    }
                """.trimIndent()
                )

                getUser(userID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePassword(userID: Int, password: String) {
        viewModelScope.launch {
            try {
                api.updateUser(userID, """
                    {
                        "password": "$password"
                    }
                """.trimIndent())
                getUser(userID)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}