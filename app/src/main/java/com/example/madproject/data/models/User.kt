package com.example.madproject.data.models

data class User (
    val id: Int,
    var firstName: String,
    var lastName: String,
    var phone: String,
    var longitude: Double,
    var latitude: Double,
)