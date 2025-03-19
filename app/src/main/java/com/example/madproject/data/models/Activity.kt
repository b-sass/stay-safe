package com.example.madproject.data.models

import java.util.Date

data class Activity (
    val id: Int,
    var name: String,
    var userID: String,
    var description: String,
    var startDate: Date,
    var startLocationID: Int,
    var endDate: Date,
    var endLocationID: Int,
    var userName: String,
    var startName: String,
    var endName: String,
    var status: String,
)