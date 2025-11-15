package com.example.staysafe.data.models

import kotlinx.serialization.Contextual
import java.util.Date
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

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
    val id: Int? = null,
    val userID: Int,
    val name: String,
    val description: String,
    val from: Int,
    val to: Int
)

@Serializable
data class ActivityLocationData (
    val id: Int? = null,
    val userID: Int,
    val name: String,
    val description: String,
    val status: String,
    @SerialName("start") var startDate: String? = null,
    @SerialName("end") var endDate: String? = null,
    val fromLat: Double,
    val fromLong: Double,
    val fromName: String,
    val toLat: Double,
    val toLong: Double,
    val toName: String,
)