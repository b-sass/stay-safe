package com.example.staysafe.data.models

data class Location (
    val id: Int,
    var name: String,
    var description: String,
    var address: String,
    var postcode: String,
    var latitude: Double,
    var longitude: Double,
)