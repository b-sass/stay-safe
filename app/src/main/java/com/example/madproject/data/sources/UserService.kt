package com.example.madproject.data.sources

import android.util.Log
import com.example.madproject.data.KtorClient
import com.example.madproject.data.models.Contact
import com.example.madproject.data.models.User
import com.example.madproject.data.models.UserContact
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
        val users: List<User> =  client.get("users/$id").body()
        return users[0]
    }

    suspend fun getUserContacts(id: Int): List<UserContact> {
        return client.get("users/contacts/$id").body()
    }

    suspend fun createUser(user: User) {
        client.post("users") {
            contentType(ContentType.Application.Json)
            Log.println(Log.INFO, "UserService", "Creating user: $user")
            setBody(user)
        }
    }

    suspend fun updateUser(id: Int, user: User) {
        client.put("users/$id") // TODO: send user object
    }

    suspend fun deleteUser(id: Int) {
        client.delete("users/$id")
    }
}
