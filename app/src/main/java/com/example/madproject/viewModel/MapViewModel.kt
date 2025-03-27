package com.example.madproject.viewModel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
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

    private val _currentLocation = MutableStateFlow<Location?>(null)
    var currentLocation = _currentLocation.asStateFlow()

    private val _userContacts = MutableStateFlow<List<User>>(emptyList())
    var userContacts = _userContacts.asStateFlow()

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
}