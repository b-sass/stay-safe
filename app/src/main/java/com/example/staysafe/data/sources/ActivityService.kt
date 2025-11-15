package com.example.staysafe.data.sources

import android.util.Log
import com.example.staysafe.data.KtorClient
import com.example.staysafe.data.models.Activity
import com.example.staysafe.data.models.ActivityLocation
import com.example.staysafe.data.models.ActivityLocationData
import com.example.staysafe.data.models.Location
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import kotlin.collections.emptyList

class ActivityService(
    private val client: HttpClient = KtorClient.client
) {

    suspend fun getActivities(): List<Activity> {
        return client.get("activities").body()
    }

    suspend fun getActivity(id: Int): Activity {
        return client.get("activities/$id").body()
    }

    suspend fun getUserActivities(id: Int, status: String?): List<ActivityLocationData> {
        val url = if (status != null) {
            "users/$id/activities?status=$status"
        } else {
            "users/$id/activities"
        }

        Log.e("Activity Service", "URL: $url")

        try {
            return client.get(url).body()
        } catch (e: Exception) {
            Log.e("Activity Service", "Could not get user activities: ${e.message}")
            return emptyList()
        }
    }

    suspend fun createActivity(activity: Activity, from: Location, to: Location) {

        val data = ActivityLocation(
            userID = activity.userID,
            name = activity.name,
            description = activity.description,
            from = from.id!!,
            to = to.id!!,
        )

        client.post("activities") {
            contentType(ContentType.Application.Json)
            setBody(data)
        }
    }

    suspend fun updateActivity(id: Int, updateData: Map<String, Any>) {
        client.put("activities/$id") {
            contentType(ContentType.Application.Json)
            setBody(updateData)
        }
    }

    suspend fun deleteActivity(id: Int) {
        client.delete("activities/$id")
    }
}
