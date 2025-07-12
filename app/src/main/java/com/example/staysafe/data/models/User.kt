package com.example.staysafe.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.util.Date

@Serializable
data class User (
    val id: Int? = null,
    @SerialName("first_name") var firstName: String,
    @SerialName("last_name") var lastName: String,
    var phone: String,
    var username: String,
    var password: String,
    var latitude: Double? = null,
    var longitude: Double? = null,
    @SerialName("UserImageURL") var image: String = "https://i.imgur.com/2aMsdby.png",
)