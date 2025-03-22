package com.example.madproject.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import java.util.Date

@Serializable
data class User (
    @SerialName("UserID") val id: Int? = null,
    @SerialName("UserFirstname") var firstName: String,
    @SerialName("UserLastname") var lastName: String,
    @SerialName("UserPhone") var phone: String,
    @SerialName("UserUsername") var userName: String,
    @SerialName("UserPassword") var password: String,
    @SerialName("UserLatitude") var latitude: Double,
    @SerialName("UserLongitude") var longitude: Double,
    @SerialName("UserTimestamp") var timeStamp: Long = Date().time,
    @SerialName("UserImageURL") var image: String = "https://i.imgur.com/2aMsdby.png",
)