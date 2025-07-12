package com.example.staysafe.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location (
    val id: Int,
    var name: String,
    var description: String,
    var address: String,
    var postcode: String,
    @SerialName("lat") var latitude: Double,
    @SerialName("long") var longitude: Double,
)