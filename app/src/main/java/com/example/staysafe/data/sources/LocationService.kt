package com.example.staysafe.data.sources

import com.example.staysafe.data.KtorClient
import com.example.staysafe.data.models.Location
import com.google.android.gms.common.util.CollectionUtils.mapOf
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlin.collections.mapOf

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

    suspend fun createLocation(userID: Int, location: Location) {
        client.post("locations") {
            contentType(ContentType.Application.Json)
            setBody("""
                {
                    "userID": $userID,
                    "name": "${location.name}",
                    "latitude": ${location.latitude},
                    "longitude": ${location.longitude}
                }
            """.trimIndent())
        }
    }

    suspend fun updateLocation(id: Int) {
        client.put("locations/$id")
    }

    suspend fun deleteLocation(id: Int) {
        client.delete("locations/$id")
    }
}