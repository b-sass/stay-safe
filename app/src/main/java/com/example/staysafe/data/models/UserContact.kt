package com.example.staysafe.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserContact (
    @SerialName("UserID") val id: Int? = null,
    @SerialName("UserFirstname") val firstName: String,
    @SerialName("UserLastname") val lastName: String,
    @SerialName("UserPhone") val phone: String,
    @SerialName("UserUsername") val username: String,
    @SerialName("UserPassword") val password: String,
    @SerialName("UserLatitude") val latitude: Double,
    @SerialName("UserLongitude") val longitude: Double,
    @SerialName("UserTimestamp") val timestamp: Long,
    @SerialName("UserImageURL") val imageURL: String,
    @SerialName("UserContactID") val contactID: Int,
    @SerialName("UserContactLabel") val label: String,
    @SerialName("UserContactDatecreated") val dateCreated: String
)