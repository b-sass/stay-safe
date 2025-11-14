package com.example.staysafe.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.Activity
import com.example.staysafe.data.models.ActivityLocationData
import com.example.staysafe.data.models.Location
import com.example.staysafe.data.models.User
import com.example.staysafe.data.repositories.ApiRepository
import com.example.staysafe.data.sources.Route
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(
): ViewModel() {

    var currentUser = mutableStateOf<User?>(null)
    val api: ApiRepository = ApiRepository()

    private val _currentLocation = MutableStateFlow<android.location.Location?>(null)
    var currentLocation = _currentLocation.asStateFlow()

    private val _userLocations = MutableStateFlow<List<Location>>(emptyList())
    var userLocations = _userLocations.asStateFlow()

    private val _userContacts = MutableStateFlow<List<User>>(emptyList())
    var userContacts = _userContacts.asStateFlow()

    private val _userActivities = MutableStateFlow<List<ActivityLocationData>>(emptyList())
    val userActivities = _userActivities.asStateFlow()

    private val _contactActivities = MutableStateFlow<List<ActivityLocationData>>(emptyList())
    val contactActivities = _contactActivities.asStateFlow()

    private val _contactLocation = MutableStateFlow<Location?>(null)
    var contactLocation = _contactLocation.asStateFlow()

    fun getUser(userID: Int) {
        viewModelScope.launch {
            currentUser.value = api.getUser(userID)
        }
    }

    fun getCurrentLocation(ctx: Context) {
        viewModelScope.launch {
            if (ctx.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                val locationProvider = LocationServices.getFusedLocationProviderClient(ctx)
                val locationRequest = CurrentLocationRequest.Builder().apply {
                    setMaxUpdateAgeMillis(0)
                    setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                }.build()

                locationProvider.getCurrentLocation(locationRequest, null)
                    .addOnSuccessListener { loc ->
                        _currentLocation.value = loc
                        Log.d("MapViewModel", "Current Location: $loc")
                    }
            }
        }
    }

    fun createActivity(activity: Activity, from: Location, to: Location) {
        viewModelScope.launch {
            try {
                api.createActivity(activity, from, to)
                api.getUserActivities(activity.userID, "active")
            } catch (e: Exception) {
                Log.e("MapViewModel", "Error creating activity: ${e.message}")
            }
        }
    }

    fun getUserLocations(userID: Int) {
        viewModelScope.launch {
            try {
                _userLocations.value = api.getUserLocations(userID)
            } catch (e: Exception) {
                Log.e("MapViewModel", "Error fetching user locations: ${e.message}")
            }
        }
    }

    fun getUserActivities(userID: Int, status: String? = null) {
        viewModelScope.launch {
            try {
                _userActivities.value = api.getUserActivities(userID, status)
            } catch (e: Exception) {
                Log.e("MapViewModel", "Error fetching user activities: ${e.message}")
            }
        }
    }

    fun getContactActivities(userID: Int) {
        viewModelScope.launch {
            try {
                val contacts = api.getUserContacts(userID)
                val contactActivities = mutableListOf<ActivityLocationData>()

                contacts.forEach { contact ->
                    val contactID = contact.id!!
                    val activities = api.getUserActivities(contactID, "active")
                    // There can only be a single active activity at a time
                    contactActivities.add(activities[0])
                }

                _contactActivities.value = contactActivities
            } catch (e: Exception) {
                Log.e("MapViewModel", "Error fetching contact activities: ${e.message}")
            }
        }
    }

    fun getRoute(from: LatLng, to: LatLng): LiveData<Route> {
        val result = MutableLiveData<Route>()
        viewModelScope.launch {
            try {
                val route = api.getRoute(from, to)
                Log.d("MapViewModel", "Route: $route")
                result.postValue(route)
            } catch (e: Exception) {
                Log.e("MapViewModel", "Error getting route: ${e.message}")
            }
        }
        return result
    }
}