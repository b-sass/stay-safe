package com.example.madproject.data.sources

import com.example.madproject.data.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post

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