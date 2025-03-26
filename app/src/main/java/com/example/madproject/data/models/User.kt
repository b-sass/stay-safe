package com.example.madproject.data.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class User (
    @SerialName("UserID") val id: Int,
    @SerialName("UserFirstname") var firstName: String,
    @SerialName("UserLastname") var lastName: String,
    @SerialName("UserPhone") var phone: String,
    @SerialName("UserUsername") var userName: String,
    @SerialName("UserPassword") var password: String = "",
    @SerialName("UserLatitude") var latitude: Double,
    @SerialName("UserLongitude") var longitude: Double,
    @SerialName("UserTimestamp") var userTimeStamp: Long = 0,
    @SerialName("UserImageURL") var userImageURL: String = "https://static.generated.photos/vue-static/face-generator/landing/wall/13.jpg",
)