package com.example.staysafe.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Serializable
data class Contact (
    @SerialName("user") val userID: Int,
    @SerialName("contact") val contactID: Int,
    var label: String
)