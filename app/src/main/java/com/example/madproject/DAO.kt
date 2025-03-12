package com.example.madproject

import androidx.room.*

@Dao
interface UsersDAO {
    @Query("SELECT * FROM usersTable")
    suspend fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

}

@Dao
interface LocationDAO {
    @Query("SELECT * FROM locationTable")
    suspend fun getAll(): List<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)
}

@Dao
interface ContactDAO {
    @Query("SELECT * FROM contactTable")
    suspend fun getAll(): List<Contact>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

}