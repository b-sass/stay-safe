package com.example.madproject.ui.theme

import android.location.Location
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="activityTable")
data class Activity(
    @PrimaryKey val activityID: Int,
    val activityName: String,
    val activityUserID: Int,
    val activityDescription: String,
    val activityFrom: Location
)
