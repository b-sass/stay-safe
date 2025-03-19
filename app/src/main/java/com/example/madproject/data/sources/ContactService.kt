package com.example.madproject.data.sources

import com.example.madproject.data.KtorClient
import com.example.madproject.data.models.Contact
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

class ContactService (
    private val client: HttpClient = KtorClient.client
) {
    suspend fun createContact() {
        client.post("contacts") // TODO: send user object
    }

    suspend fun deleteContact(id: Int) {
        client.delete("contacts/$id")
    }
}