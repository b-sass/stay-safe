package com.example.madproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.Activity
import com.example.madproject.data.models.Location
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

    private val _coordinates = MutableStateFlow<Pair<Double, Double>?>(null)
    var coordinates = _coordinates.asStateFlow()

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

    fun createActivity(name: String, description: String, startDate: String, endDate: String, start: Location, end: Location) {
        var locations: List<Location>? = null
        viewModelScope.launch {
            api.createLocation(start)
        }.invokeOnCompletion {
            viewModelScope.launch {
                api.createLocation(end)
        }.invokeOnCompletion {
            viewModelScope.launch {
                locations = api.getLocations()
        }.invokeOnCompletion {
            viewModelScope.launch {
                val locEnd = locations!!.last()
                val locStart = locations[locations.size - 2]

                api.createActivity(
                    Activity(
                        name = name,
                        description = description,
                        userID = userID!!,
                        startDate = startDate,
                        endDate = endDate,
                        endLocationID = locEnd.id!!,
                        startLocationID = locStart.id!!,
                        userName = _user.value?.userName!!,
                        startName = locStart.name,
                        endName = locEnd.name,
                        statusID = 1,
                        status = "Planned",
                    )
                )
            }.invokeOnCompletion {
                // Refresh activities list
                getActivities(userID!!)
            }
        }
    }}}

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

    fun getLocation(address: String, postcode: String): Pair<Double,Double>? {
        viewModelScope.launch {
            _coordinates.value = api.getLocationCoordinates(address, postcode)
        }
        return _coordinates.value
    }
}