package com.example.staysafe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.Contact
import com.example.staysafe.data.models.User
import com.example.staysafe.data.models.UserContact
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel: ViewModel() {

    private val api = ApiRepository()

    var userID: Int? = null

    private val _contacts = MutableStateFlow<List<UserContact>?>(null)
    var contacts = _contacts.asStateFlow()

    fun getContacts(userID: Int) {
        viewModelScope.launch {
            _contacts.value = api.getUserContacts(userID)
        }
    }

    fun addContact(name: String, label: String) {
        viewModelScope.launch {
            try {
                val users: List<User> = api.getUsers()
                users.find { it.userName == name }?.let {
                    api.createContact(
                        Contact(
                            userID = userID!!,
                            contactID = it.id!!,
                            label = label
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.invokeOnCompletion {
            // Refresh contacts list
            getContacts(userID!!)
        }
    }

    fun deleteContact(contactID: Int) {
        viewModelScope.launch {
            api.deleteContact(contactID)
        }.invokeOnCompletion {
            // Refresh contacts list
            getContacts(userID!!)
        }
    }
}