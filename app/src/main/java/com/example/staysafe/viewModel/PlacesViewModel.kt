package com.example.staysafe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.Location
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object PlacesViewModel: ViewModel() {

    val api = ApiRepository()
    private val _locations = MutableStateFlow<List<Location>>(emptyList())
    var locations = _locations.asStateFlow();

    var userID: Int? = null

    fun getUserPlaces() {
        viewModelScope.launch {
            _locations.value = api.getUserLocations(userID!!)
        }
    }

    fun createPlace(place: Location) {
        viewModelScope.launch {
            api.createLocation(userID!!, place)
            getUserPlaces()
        }
    }

    fun deletePlace(placeID: Int) {
        viewModelScope.launch {
            api.deleteLocation(placeID)
            getUserPlaces()
        }
    }
}