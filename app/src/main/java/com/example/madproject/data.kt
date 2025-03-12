package com.example.madproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="usersTable")
data class User(
    val userFirstName: String,
    val userLastName: String,
    val userUserName: String,
    @PrimaryKey(autoGenerate = true) val userID: Int
){
    val fullName: String
        get() = "$userFirstName $userLastName"
}

@Entity(tableName="locationTable")
data class Location(
    val long: Double,
    val lat: Double,
    @PrimaryKey(autoGenerate = true) val locationID: Int
)

@Entity(tableName="contactTable")
data class Contact(
    val contactUserID: Int,
    val contactContactID: Int,
    val contactLabel: String,
    val contactDataCreated: Long,
    @PrimaryKey(autoGenerate = true) val contactID: Int
)
