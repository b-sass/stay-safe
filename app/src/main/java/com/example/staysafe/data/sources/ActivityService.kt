package com.example.staysafe.data.sources

import com.example.staysafe.data.KtorClient
import com.example.staysafe.data.models.Activity
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

class ActivityService(
    private val client: HttpClient = KtorClient.client
) {

    suspend fun getActivities(): List<Activity> {
        return client.get("activities").body()
    }

    suspend fun getActivity(id: Int): Activity {
        return client.get("activities/$id").body()
    }

    suspend fun getUserActivities(id: Int): List<Activity> {
        val response: HttpResponse = client.get("activities/users/$id")
        // Check for status
        if (response.status.value in 200..299) {
            return response.body()
        }
        return emptyList()
    }

    suspend fun createActivity(activity: Activity) {
        client.post("activities") {
            contentType(ContentType.Application.Json)
            setBody(activity)
        }
    }

    suspend fun updateActivity(id: Int) {
        client.put("activities/$id")
    }

    suspend fun deleteActivity(id: Int) {
        client.delete("activities/$id")
    }
}
