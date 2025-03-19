package com.example.madproject.data.repositories

import com.example.madproject.data.models.User
import com.example.madproject.data.sources.ActivityService
import com.example.madproject.data.sources.ContactService
import com.example.madproject.data.sources.LocationService
import com.example.madproject.data.sources.StatusService
import com.example.madproject.data.sources.UserService

class ApiRepository (
    private val userService: UserService = UserService(),
    private val contactService: ContactService = ContactService(),
    private val activityService: ActivityService = ActivityService(),
    private val locationService: LocationService = LocationService(),
    private val statusService: StatusService = StatusService(),
) {
    suspend fun getUsers(): List<User> {
        return userService.getUsers()
    }
}