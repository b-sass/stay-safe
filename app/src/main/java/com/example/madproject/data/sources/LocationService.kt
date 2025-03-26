package com.example.madproject.data.sources

import com.example.madproject.data.KtorClient
import com.example.madproject.data.models.Location
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

class LocationService (
    private val client: HttpClient = KtorClient.client
) {

    suspend fun getLocations(): List<Location> {
        return client.get("locations").body()
    }

    suspend fun getLocation(id: Int): Location {
        return client.get("locations/$id").body()
    }

    suspend fun createLocation(user: Location) {
        client.post("locations")
    }

    suspend fun updateLocation(id: Int) {
        client.put("locations/$id")
    }

    suspend fun deleteLocation(id: Int) {
        client.delete("locations/$id")
    }
}