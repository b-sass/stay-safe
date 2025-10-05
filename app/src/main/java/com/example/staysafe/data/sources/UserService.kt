package com.example.staysafe.data.sources

import android.util.Log
import com.example.staysafe.data.KtorClient
import com.example.staysafe.data.models.Location
import com.example.staysafe.data.models.User
import com.example.staysafe.data.models.UserContact
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class UserService(
    private val client: HttpClient = KtorClient.client
) {

    suspend fun getUsers(): List<User> {
        return client.get("users").body()
    }

    suspend fun getUser(id: Int): User {
        val user: User =  client.get("users/$id").body()
        return user
    }

    suspend fun loginUser(username: String, password: String): User? {
        return try {
            client.post("users/login") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("username" to username, "password" to password))
            }.body<User>()
        } catch (e: Exception) {
            Log.e("UserService", "Login failed: ${e.message}")
            null
        }
    }

    suspend fun getUserContacts(id: Int): List<UserContact> {
        return try {
            client.get("users/$id/contacts").body()
        } catch (e: Exception) {
            emptyList();
        }
    }

    suspend fun getUserLocations(id: Int): List<Location> {
        return try {
            client.get("users/$id/locations").body()
        } catch (e: Exception) {
            emptyList();
        }
    }

    suspend fun createUser(user: User) {
        client.post("users") {
            contentType(ContentType.Application.Json)
            Log.println(Log.INFO, "UserService", "Creating user: $user")
            setBody(user)
        }
    }

    suspend fun updateUser(id: Int, request: String) {
        client.put("users/$id") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    suspend fun deleteUser(id: Int) {
        client.delete("users/$id")
    }
}
