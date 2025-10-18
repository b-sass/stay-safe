package com.example.staysafe.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.Activity
import com.example.staysafe.data.models.Location
import com.example.staysafe.data.models.User
import com.example.staysafe.data.repositories.ApiRepository
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel(

): ViewModel() {

//    val userLocation = LocationManager().getCurrentLocation()
        // Handle location updates
    var currentUser = mutableStateOf<User?>(null)
    val api: ApiRepository = ApiRepository()

    private val _currentLocation = MutableStateFlow<android.location.Location?>(null)
    var currentLocation = _currentLocation.asStateFlow()

    private val _userLocations = MutableStateFlow<List<Location>>(emptyList())
    var userLocations = _userLocations.asStateFlow()

    private val _userContacts = MutableStateFlow<List<User>>(emptyList())
    var userContacts = _userContacts.asStateFlow()

    private val _contactActivities = MutableStateFlow<List<Activity>>(emptyList())
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

    fun getContactActivities(userID: Int) {
        viewModelScope.launch {
            try {
                val contacts = api.getUserContacts(userID)
                val allActivities = mutableListOf<Activity>()

                contacts.forEach { contact ->
                    val contactID = contact.id ?: return@forEach
                    val activities = api.getUserActivities(contactID)
                    // Filter for activities with "Started" status
                    val startedActivities = activities.filter { it.status == "Started" }
                    allActivities.addAll(startedActivities)
                }

                _contactActivities.value = allActivities
            } catch (e: Exception) {
                Log.e("MapViewModel", "Error fetching contact activities: ${e.message}")
            }
        }
    }

}