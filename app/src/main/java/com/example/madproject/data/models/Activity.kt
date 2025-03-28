package com.example.madproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Activity (
    @SerialName ("ActivityID") val id: Int? = null,
    @SerialName ("ActivityName") var name: String,
    @SerialName ("ActivityUserID") var userID: Int,
    @SerialName("ActivityDescription") var description: String,
    @SerialName("ActivityLeave") var startDate: String,
    @SerialName("ActivityFromID") var startLocationID: Int,
    @SerialName("ActivityArrive") var endDate: String,
    @SerialName("ActivityToID")var endLocationID: Int,
    @SerialName("ActivityUsername") var userName: String,
    @SerialName("ActivityFromName") var startName: String,
    @SerialName("ActivityToName") var endName: String,
    @SerialName("ActivityStatusID") var statusID: Int? = null,
    @SerialName("ActivityStatusName") var status: String,
)