package com.example.staysafe.data.models

import kotlinx.serialization.Contextual
import java.util.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activity (
    val id: Int,
    var userID: Int,
    var name: String,
    var description: String,
    @Contextual
    @SerialName("start") var startDate: Date?,
    @Contextual
    @SerialName("end") var endDate: Date,
    var status: String = "active",
)

data class ActivityLocation (
    val id: Int,
    var userID: Int,
    var name: String,
    var description: String,
    @Contextual
    @SerialName("start") var startDate: Date?,
    @Contextual
    @SerialName("end") var endDate: Date,
    var status: String = "active",
    var startLocationID: Int,
    var endLocationID: Int,
    var startName: String,
    var endName: String,
)