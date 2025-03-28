package com.example.madproject.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.Activity
import com.example.madproject.data.models.Location
import com.example.madproject.data.models.RouteMatrix
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(

): ViewModel() {

//    val userLocation = LocationManager().getCurrentLocation()
        // Handle location updates
    var currentUser = mutableStateOf<User?>(null)
    val api: ApiRepository = ApiRepository()

    private val _currentLocation = MutableStateFlow<android.location.Location?>(null)
    var currentLocation = _currentLocation.asStateFlow()

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

    fun getUserContacts(userID: Int) {
//        viewModelScope.launch {
//            _userContacts.value = api.getUserContacts(userID)
//        }
    }

    suspend fun fetchLocation(id: Int): com.example.madproject.data.models.Location? {
        return try {
            api.getLocation(id)
        } catch (e: Exception) {
            null
        }
    }

    fun getLocation(locationId: Int) {
        viewModelScope.launch {
            try {
                _contactLocation.value = api.getLocation(locationId)
            } catch (e: Exception) {
                Log.e("MapViewModel", "Error fetching location: ${e.message}")
            }
        }
    }

    fun updateLocation(lat: Double, lon: Double) {
        currentUser.value!!.latitude = lat
        currentUser.value!!.longitude = lon

        viewModelScope.launch {
            api.updateUser(currentUser.value?.id!!, currentUser.value!!)
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

    fun getRouteMatrix(origin: LatLng, destination: LatLng, callback: (Result<RouteMatrix>) -> Unit) {
        viewModelScope.launch {
            try {
                val result = api.getRouteMatrix(origin, destination)
                callback(result)
            } catch (e: Exception) {
                callback(Result.failure(e))
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