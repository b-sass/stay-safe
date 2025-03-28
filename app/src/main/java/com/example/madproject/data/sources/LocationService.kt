package com.example.madproject.data.sources

import com.example.madproject.data.KtorClient
import com.example.madproject.data.models.Location
import com.example.madproject.data.models.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class LocationService (
    private val client: HttpClient = KtorClient.client
) {

    suspend fun getLocations(): List<Location> {
        return client.get("locations").body()
    }

    suspend fun getLocation(id: Int): Location {
        val locations: List<Location> =  client.get("locations/$id").body()
        return locations[0]
    }

    suspend fun createLocation(location: Location) {
        client.post("locations") {
            contentType(ContentType.Application.Json)
            setBody(location)
        }
    }

    suspend fun updateLocation(id: Int) {
        client.put("locations/$id")
    }

    suspend fun deleteLocation(id: Int) {
        client.delete("locations/$id")
    }
}