package com.example.staysafe.data.repositories

import com.example.staysafe.data.models.Activity
import com.example.staysafe.data.models.Contact
import com.example.staysafe.data.models.Location
import com.example.staysafe.data.models.User
import com.example.staysafe.data.models.UserContact
import com.example.staysafe.data.sources.ActivityService
import com.example.staysafe.data.sources.ContactService
import com.example.staysafe.data.sources.LocationService
import com.example.staysafe.data.sources.UserService

class ApiRepository (
    private val userAPI: UserService = UserService(),
    private val contactAPI: ContactService = ContactService(),
    private val activityAPI: ActivityService = ActivityService(),
    private val locationAPI: LocationService = LocationService(),
) {
    // User
    suspend fun getUsers(): List<User> { return userAPI.getUsers() }
    suspend fun getUser(id: Int): User { return userAPI.getUser(id) }
    suspend fun getUserContacts(id: Int): List<UserContact> { return userAPI.getUserContacts(id) }
    suspend fun getUserLocations(id: Int): List<Location> { return userAPI.getUserLocations(id) }
    suspend fun createUser(user: User) { userAPI.createUser(user) }
    suspend fun updateUser(id: Int, user: User) { userAPI.updateUser(id, user) }
    suspend fun loginUser(username: String, password: String): User? { return userAPI.loginUser(username, password) }
    suspend fun deleteUser(id: Int) { userAPI.deleteUser(id) }

    // Contact
    suspend fun createContact(contact: Contact) { contactAPI.createContact(contact) }
    suspend fun deleteContact(userID: Int, contactID: Int) { contactAPI.deleteContact(userID, contactID) }

    // Activity
    suspend fun getActivities(): List<Activity> { return activityAPI.getActivities() }
    suspend fun getActivity(id: Int): Activity { return activityAPI.getActivity(id) }
    suspend fun getUserActivities(id: Int): List<Activity> { return activityAPI.getUserActivities(id) }
    suspend fun createActivity(activity: Activity) { activityAPI.createActivity(activity) }
    suspend fun updateActivity(id: Int) { activityAPI.updateActivity(id) }
    suspend fun deleteActivity(id: Int) { activityAPI.deleteActivity(id) }

    // Location
    suspend fun getLocations(): List<Location> { return locationAPI.getLocations() }
    suspend fun getLocation(id: Int): Location { return locationAPI.getLocation(id) }
    suspend fun createLocation(userID: Int, location: Location) { locationAPI.createLocation(userID, location) }
    suspend fun updateLocation(id: Int) { locationAPI.updateLocation(id) }
    suspend fun deleteLocation(id: Int) { locationAPI.deleteLocation(id) }
}