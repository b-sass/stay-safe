package com.example.madproject

import androidx.room.*

@Dao
interface DAO {
    @Query("SELECT * FROM usersTable")
    suspend fun getAll(): List<User>
}