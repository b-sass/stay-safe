package com.example.madproject.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date

@Serializable
data class Contact (
    @SerialName("ContactID") val id: Int? = null,
    @SerialName("ContactUserID") val userID: Int,
    @SerialName("ContactContactID") val contactID: Int,
    @SerialName("ContactLabel") var label: String,
    @SerialName("ContactDatecreated") val dateCreated: String = LocalDateTime.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME)
)