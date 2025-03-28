package com.example.madproject.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class Location (
    @SerialName("LocationID") val id: Int? = null,
    @SerialName("LocationName") var name: String,
    @SerialName("LocationDescription") var description: String,
    @SerialName("LocationAddress") var address: String,
    @SerialName("LocationPostcode") var postcode: String,
    @SerialName("LocationLatitude") var latitude: Double,
    @SerialName("LocationLongitude") var longitude: Double,
)