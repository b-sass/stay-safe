package com.example.staysafe.data.sources

import android.util.Log
import com.example.staysafe.BuildConfig
import com.example.staysafe.data.KtorClient.client
import com.example.staysafe.data.models.Location
import com.google.android.gms.common.util.MapUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.maps.android.PolyUtil
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.request
import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

data class Route(
    val distance: Int,
    val duration: Int,
    val polyline: List<LatLng>
)
class RouteSource {
    val routingURL = "https://routes.googleapis.com/directions/v2:computeRoutes"

    @OptIn(ExperimentalSerializationApi::class)
    @Serializable
    @JsonIgnoreUnknownKeys
    data class RouteListResponse(
        @Contextual
        val routes: List<RouteResponse>
    )
    @Serializable
    data class RouteResponse(
        val distanceMeters: Int,
        val duration: String,
        @Contextual
        val polyline: PolylineResponse
    )
    @Serializable
    data class PolylineResponse(
        val encodedPolyline: String
    )

    suspend fun getRoute(from: LatLng, to: LatLng): Route {

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
            val routeRequest = client.post(routingURL) {
                headers["X-Goog-Api-Key"] = BuildConfig.MAPS_API_KEY
                headers["X-Goog-FieldMask"] = "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline"
                setBody(request)
            }
            Log.d("Route Service", "Request: $request")
            Log.d("Route Service", "Got a route! ${routeRequest.body<String>()}")
            Log.d("Route Service", "Got a route (class)! ${routeRequest.body<RouteListResponse>()}")

            val route = routeRequest.body<RouteListResponse>().routes[0]
            val polyline = PolyUtil.decode(route.polyline.encodedPolyline)

            return Route(route.distanceMeters, route.duration.slice(0..<route.duration.length-1).toInt(), polyline)
        } catch (e: Exception) {
            Log.e("Route Service", "Cannot get a route: ${e.message}")

            return Route(0, 0, emptyList())
        }
    }
}