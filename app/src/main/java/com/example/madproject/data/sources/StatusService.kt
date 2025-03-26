package com.example.madproject.data.sources

import com.example.madproject.data.KtorClient
import com.example.madproject.data.models.Status
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

class StatusService (
    private val client: HttpClient = KtorClient.client
) {

    suspend fun getStatus(): List<Status> {
        return client.get("status").body()
    }

    suspend fun getStatus(id: Int): Status {
        return client.get("status/$id").body()
    }
}