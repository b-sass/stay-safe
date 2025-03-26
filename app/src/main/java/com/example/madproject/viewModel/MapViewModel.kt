package com.example.madproject.viewModel

import android.location.LocationManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.User
import com.example.madproject.data.repositories.ApiRepository
import kotlinx.coroutines.launch

class MapViewModel(

): ViewModel() {

//    val userLocation = LocationManager().getCurrentLocation()
        // Handle location updates
    var currentUser: User? = null
    val api: ApiRepository = ApiRepository()

    fun getUser(userID: Int) {
        viewModelScope.launch {
            currentUser = api.getUser(userID)
        }
    }


}