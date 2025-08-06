package com.example.staysafe.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserContact (
    val id: Int? = null,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val phone: String,
    val username: String,
    val password: String,
    val latitude: Double?,
    val longitude: Double?,
//    @SerialName("UserImageURL") val imageURL: String,
//    @SerialName("UserContactID") val contactID: Int,
    val label: String
)