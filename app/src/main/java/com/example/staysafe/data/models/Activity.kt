package com.example.staysafe.data.models

import kotlinx.serialization.Contextual
import java.util.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Activity (
    val id: Int,
    var name: String,
    @SerialName("user") var userID: String,
    var description: String,
    @Contextual
    @SerialName("start") var startDate: Date,
    var startLocationID: Int,
    @Contextual
    @SerialName("end") var endDate: Date,
    var endLocationID: Int,
    var startName: String,
    var endName: String,
    var status: String,
)