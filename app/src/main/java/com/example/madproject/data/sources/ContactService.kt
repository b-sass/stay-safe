package com.example.madproject.data.sources

import com.example.madproject.data.KtorClient
import com.example.madproject.data.models.Contact
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ContactService (
    private val client: HttpClient = KtorClient.client
) {
    suspend fun createContact(contact: Contact) {
        client.post("contacts") {
            contentType(ContentType.Application.Json)
            setBody(contact)
        }
    }

    suspend fun deleteContact(id: Int) {
        client.delete("contacts/$id")
    }
}