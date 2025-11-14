package com.example.staysafe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.Activity
import com.example.staysafe.data.models.ActivityLocationData
import com.example.staysafe.data.models.Location
import com.example.staysafe.data.models.User
import com.example.staysafe.data.models.UserContact
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {

    val api = ApiRepository()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    private val _contacts = MutableStateFlow<List<UserContact>>(emptyList())
    val contacts = _contacts.asStateFlow()

    private val _activities = MutableStateFlow<List<ActivityLocationData>>(emptyList())
    val activities = _activities.asStateFlow()

    private val _places = MutableStateFlow<List<Location>>(emptyList())
    val places = _places.asStateFlow()

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

    fun getUserContacts(userID: Int) {
        viewModelScope.launch {
            try {
                _contacts.value = api.getUserContacts(userID)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUserActivities(userID: Int, status: String? = null) {
        viewModelScope.launch {
            try {
                _activities.value = api.getUserActivities(userID, status)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUserPlaces(userID: Int) {
        viewModelScope.launch {
            try {
                _places.value = api.getUserLocations(userID)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}