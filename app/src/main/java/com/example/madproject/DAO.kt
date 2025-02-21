package com.example.madproject

import androidx.room.Query

@DAO
interface DAO {
    @Query("SELECT * FROM usersTable")
    suspend fun getAll(): List<User>
}