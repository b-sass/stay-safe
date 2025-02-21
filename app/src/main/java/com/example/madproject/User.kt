package com.example.madproject

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="usersTable")
data class User(
    @PrimaryKey val userID: Int,
    val userFirstName: String,
    val userLastName: String,
    val userUserName: String,
)
