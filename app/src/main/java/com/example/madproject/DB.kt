package com.example.madproject

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User ::class, Location::class, Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() { // Renamed to AppDatabase
    abstract fun usersDAO(): UsersDAO
    abstract fun locationDAO(): LocationDAO
    abstract fun contactDAO(): ContactDAO
}