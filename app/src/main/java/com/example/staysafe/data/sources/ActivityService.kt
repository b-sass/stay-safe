package com.example.staysafe.data.sources

import com.example.staysafe.data.KtorClient
import com.example.staysafe.data.models.Activity
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

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
        return client.get("activities/users/$id").body()
    }

    suspend fun createActivity(activity: Activity) {
        client.post("activities")
    }

    suspend fun updateActivity(id: Int) {
        client.put("activities/$id")
    }

    suspend fun deleteActivity(id: Int) {
        client.delete("activities/$id")
    }
}
