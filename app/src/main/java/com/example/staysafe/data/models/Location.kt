package com.example.staysafe.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val id: Int,
    var name: String,
    var latitude: Double,
    var longitude: Double,
)