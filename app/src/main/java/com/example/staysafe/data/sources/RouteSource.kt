package com.example.staysafe.data.sources

import android.util.Log
import com.example.staysafe.BuildConfig
import com.example.staysafe.data.KtorClient.client
import com.example.staysafe.data.models.Location
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import kotlinx.serialization.json.Json

data class Route(
    val distance: Int,
    val duration: Int,
    val polyline: List<Map<Double, Double>>
)
class RouteSource {
    val routingURL = "https://routes.googleapis.com/directions/v2:computeRoutes"

    suspend fun getRoute(from: Location, to: Location): Route {

        val request = """
            {
                "origin": {
                    "location": {
                        "latLng": {
                            "latitude": ${from.latitude},
                            "longitude": ${from.longitude}
                        }
                    }
                },
                "destination": {
                    "location": {
                        "latLng": {
                            "latitude": ${to.latitude},
                            "longitude": ${to.longitude}
                        }
                    }
                }
            }
        """.trimIndent()

        try {
            val route = client.post(routingURL) {
                headers["X-Goog-Api-Key"] = BuildConfig.MAPS_API_KEY
                headers["X-Goog-FieldMask"] = "routes.duration,routes.distanceMeters,routes.polyline.geo_json_linestring"
                setBody(request)
            }
            Log.d("Route Service", "Request: $request")
            Log.d("Route Service", "Got a route! ${route.body<String>()}")
        } catch (e: Exception) {
            Log.e("Route Service", "Cannot get a route: ${e.message}")
        }

        return Route(0, 0, emptyList())
    }
}