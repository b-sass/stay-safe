package com.example.staysafe.data.sources

import com.example.staysafe.data.KtorClient
import com.example.staysafe.data.models.Status
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

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