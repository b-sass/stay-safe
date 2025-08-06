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
    private val users: UserService = UserService(),
    private val contacts: ContactService = ContactService(),
    private val activities: ActivityService = ActivityService(),
    private val locations: LocationService = LocationService(),
) {
    // User
    suspend fun getUsers(): List<User> { return users.getUsers() }
    suspend fun getUser(id: Int): User { return users.getUser(id) }
    suspend fun getUserContacts(id: Int): List<UserContact> { return users.getUserContacts(id) }
    suspend fun createUser(user: User) { users.createUser(user) }
    suspend fun updateUser(id: Int, user: User) { users.updateUser(id, user) }
    suspend fun loginUser(username: String, password: String): User? { return users.loginUser(username, password) }
    suspend fun deleteUser(id: Int) { users.deleteUser(id) }

    // Contact
    suspend fun createContact(contact: Contact) { contacts.createContact(contact) }
    suspend fun deleteContact(userID: Int, contactID: Int) { contacts.deleteContact(userID, contactID) }

    // Activity
    suspend fun getActivities() { activities.getActivities() }
    suspend fun getActivity(id: Int) { activities.getActivity(id) }
    suspend fun getUserActivities(id: Int) { activities.getUserActivities(id) }
    suspend fun createActivity(activity: Activity) { activities.createActivity(activity) }
    suspend fun updateActivity(id: Int) { activities.updateActivity(id) }
    suspend fun deleteActivity(id: Int) { activities.deleteActivity(id) }

    // Location
    suspend fun getLocations() { locations.getLocations() }
    suspend fun getLocation(id: Int) { locations.getLocation(id) }
    suspend fun createLocation(location: Location) { locations.createLocation(location) }
    suspend fun updateLocation(id: Int) { locations.updateLocation(id) }
    suspend fun deleteLocation(id: Int) { locations.deleteLocation(id) }
}