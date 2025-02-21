package com.example.madproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="contactTable")
data class Contact(
    @PrimaryKey val contactID: Int,
    val contactUserID: Int,
    val contactContactID: Int,
    val contactLabel: String,
    val contactDataCreated: Long
)
