package com.example.madproject.data.models

import java.util.Date

data class Activity (
    val id: Int,
    var userID: String,
    var description: String,
    var startLocationID: String,
    var endLocationID: String,
    var name: String,
    var arrivalDate: Date,
    var status: String,
    var departureDate: Date,
)