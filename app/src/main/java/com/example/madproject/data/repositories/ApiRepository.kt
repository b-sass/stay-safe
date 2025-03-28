package com.example.madproject.data.repositories

import com.example.madproject.data.models.Activity
import com.example.madproject.data.models.Contact
import com.example.madproject.data.models.Location
import com.example.madproject.data.models.User
import com.example.madproject.data.models.UserContact
import com.example.madproject.data.sources.ActivityService
import com.example.madproject.data.sources.ContactService
import com.example.madproject.data.sources.LocationService
import com.example.madproject.data.sources.StatusService
import com.example.madproject.data.sources.UserService

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
    suspend fun getLocations() { locations.getLocations() }
    suspend fun getLocation(id: Int) { locations.getLocation(id) }
    suspend fun createLocation(location: Location) { locations.createLocation(location) }
    suspend fun updateLocation(id: Int) { locations.updateLocation(id) }
    suspend fun deleteLocation(id: Int) { locations.deleteLocation(id) }

    // Status
    suspend fun getStatus() { status.getStatus() }
    suspend fun getStatus(id: Int) { status.getStatus(id) }
}