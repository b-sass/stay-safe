package com.example.madproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.Activity
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ActivityViewModel: ViewModel() {
    var userID: Int? = null
    private val api = ApiRepository()

    private val _activities = MutableStateFlow<List<Activity>?>(null)
    var activities = _activities.asStateFlow()

    private val _user = MutableStateFlow<User?>(null)
    var user = _user.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            _user.value = api.getUser(userID!!)
        }
    }

    fun getActivities(userID: Int) {
        viewModelScope.launch {
            _activities.value = api.getUserActivities(userID)
        }
    }

    fun createActivity(activity: Activity) {
        viewModelScope.launch {
            api.createActivity(activity)
        }.invokeOnCompletion {
            // Refresh activities list
            getActivities(userID!!)
        }
    }

    fun updateActivity(activity: Activity) {
        viewModelScope.launch {
            api.updateActivity(activity.id!!)
        }.invokeOnCompletion {
            // Refresh activities list
            getActivities(userID!!)
        }
    }

    fun deleteActivity(activityID: Int) {
        viewModelScope.launch {
            api.deleteActivity(activityID)
        }.invokeOnCompletion {
            // Refresh activities list
            getActivities(userID!!)
        }
    }
}