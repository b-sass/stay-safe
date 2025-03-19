package com.example.madproject.data.models

import java.util.Date

data class Contact (
    val id: Int,
    val userID: String,
    val contactID: String,
    val label: String,
    val dateCreated: String = Date().toString()
)