package com.example.staysafe.data.models

import kotlinx.serialization.Contextual
import java.util.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activity (
    val id: Int? = null,
    var userID: Int,
    var name: String,
    var description: String,
    @Contextual
    @SerialName("start") var startDate: Date? = null,
    @Contextual
    @SerialName("end") var endDate: Date? = null,
    var status: String = "active",
)

@Serializable
data class ActivityLocation (
    var userID: Int,
    var name: String,
    var description: String,
    var from: Int,
    var to: Int,
)