package com.example.madproject.data.repositories

import com.example.madproject.BuildConfig
import com.example.madproject.data.KtorClient
import com.example.madproject.data.KtorClient.client
import com.example.madproject.data.models.Activity
import com.example.madproject.data.models.Contact
import com.example.madproject.data.models.Location
import com.example.madproject.data.models.RouteMatrix
import com.example.madproject.data.models.User
import com.example.madproject.data.models.UserContact
import com.example.madproject.data.sources.ActivityService
import com.example.madproject.data.sources.ContactService
import com.example.madproject.data.sources.LocationService
import com.example.madproject.data.sources.StatusService
import com.example.madproject.data.sources.UserService
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.URLEncoder
import com.google.android.gms.maps.model.LatLng
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import kotlinx.serialization.json.*
import kotlinx.coroutines.CancellationException
import kotlin.text.get

class ApiRepository (
    private val users: UserService = UserService(),
    private val contacts: ContactService = ContactService(),
    private val activities: ActivityService = ActivityService(),
    private val locations: LocationService = LocationService(),
    private val status: StatusService = StatusService(),
) {
    // User
    suspend fun getUsers(): List<User> { return users.getUsers() }
    suspend fun getUser(id: Int): User { return users.getUser(id) }
    suspend fun getUserContacts(id: Int): List<UserContact> { return users.getUserContacts(id) }
    suspend fun createUser(user: User) { users.createUser(user) }
    suspend fun updateUser(id: Int, user: User) { users.updateUser(id, user) }
    suspend fun deleteUser(id: Int) { users.deleteUser(id) }

    // Contact
    suspend fun createContact(contact: Contact) { contacts.createContact(contact) }
    suspend fun deleteContact(id: Int) { contacts.deleteContact(id) }

    // Activity
    suspend fun getActivities(): List<Activity> { return activities.getActivities() }
    suspend fun getActivity(id: Int): Activity { return activities.getActivity(id) }
    suspend fun getUserActivities(id: Int): List<Activity> { return activities.getUserActivities(id) }
    suspend fun createActivity(activity: Activity) { activities.createActivity(activity) }
    suspend fun updateActivity(id: Int) { activities.updateActivity(id) }
    suspend fun deleteActivity(id: Int) { activities.deleteActivity(id) }

    // Location
    suspend fun getLocations(): List<Location> { return locations.getLocations() }
    suspend fun getLocation(id: Int): Location { return locations.getLocation(id) }
    suspend fun createLocation(location: Location) { locations.createLocation(location) }
    suspend fun updateLocation(id: Int) { locations.updateLocation(id) }
    suspend fun deleteLocation(id: Int) { locations.deleteLocation(id) }

    // Status
    suspend fun getStatus() { status.getStatus() }
    suspend fun getStatus(id: Int) { status.getStatus(id) }

    // Geolocation
    suspend fun getLocationCoordinates(address: String, postcode: String): Pair<Double, Double>? {
        try {
            val client = KtorClient.client

            val apiKey = BuildConfig.MAPS_API_KEY // Access from BuildConfig
            val fullAddress = "$address, $postcode"
            val encodedAddress = URLEncoder.encode(fullAddress, "UTF-8")
            val url = "https://maps.googleapis.com/maps/api/geocode/json?key=$apiKey&address=$encodedAddress"

            val response = client.get(url)

            if (response.status.isSuccess()) {
                val responseBody = response.body<JsonObject>()

                if (responseBody["status"]?.jsonPrimitive?.content == "OK") {
                    val results = responseBody["results"]?.jsonArray
                    if (results != null && results.isNotEmpty()) {
                        val location = results.first().jsonObject["navigation_points"]
                            ?.jsonObject["location"]?.jsonObject

                        val lat = location?.getValue("latitude")?.jsonPrimitive?.doubleOrNull
                        val lng = location?.getValue("longitude")?.jsonPrimitive?.doubleOrNull

                        if (lat != null && lng != null) {
                            return Pair(lat, lng)
                        }
                    }
                }
            }
            return null
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getRouteMatrix(origin: LatLng, destination: LatLng): Result<RouteMatrix> {
        return try {
            val apiKey = BuildConfig.MAPS_API_KEY
            val url = "https://routes.googleapis.com/directions/v2:computeRoutes"

            val requestBody = buildJsonObject {
                put("origin", buildJsonObject {
                    put("location", buildJsonObject {
                        put("latLng", buildJsonObject {
                            put("latitude", JsonPrimitive(origin.latitude))
                            put("longitude", JsonPrimitive(origin.longitude))
                        })
                    })
                })
                put("destination", buildJsonObject {
                    put("location", buildJsonObject {
                        put("latLng", buildJsonObject {
                            put("latitude", JsonPrimitive(destination.latitude))
                            put("longitude", JsonPrimitive(destination.longitude))
                        })
                    })
                })
                put("travelMode", JsonPrimitive("DRIVE"))
                put("routingPreference", JsonPrimitive("TRAFFIC_AWARE"))
                put("computeAlternativeRoutes", JsonPrimitive(false))
            }

            val response = client.post(url) {
                contentType(ContentType.Application.Json)
                header("X-Goog-Api-Key", apiKey)
                header("X-Goog-FieldMask", "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline")
                setBody(requestBody)
            }

            if (response.status.isSuccess()) {
                val responseData: JsonObject = response.body()
                val routes = responseData["routes"]?.jsonArray

                if (routes != null && routes.isNotEmpty()) {
                    val route = routes[0].jsonObject
                    val distanceMeters = route["distanceMeters"]?.jsonPrimitive?.intOrNull ?: 0
                    val durationSeconds = route["duration"]?.jsonObject
                        ?.get("seconds")?.jsonPrimitive?.intOrNull ?: 0
                    val polyline = route["polyline"]?.jsonObject
                        ?.get("encodedPolyline")?.jsonPrimitive?.content

                    Result.success(RouteMatrix(distanceMeters, durationSeconds, polyline))
                } else {
                    Result.failure(Exception("No routes found"))
                }
            } else {
                Result.failure(Exception("HTTP error: ${response.status.value}"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}