package com.example.staysafe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.Activity
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class ActivityViewModel: ViewModel() {
    private val api = ApiRepository()
    var userID: Int? = null
    var activities: List<Activity>? = null

    init {
        getActivities(userID!!)
    }

    fun getActivities(userID: Int) {
        viewModelScope.launch {
            try {
                activities = api.getActivities()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}