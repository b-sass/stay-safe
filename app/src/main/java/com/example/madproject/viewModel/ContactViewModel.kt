package com.example.madproject.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.madproject.data.models.Contact
import com.example.madproject.data.models.User
import com.example.madproject.data.models.UserContact
import com.example.madproject.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel: ViewModel() {

    private val api = ApiRepository()

    private val _contacts = MutableStateFlow<List<UserContact>?>(null)
    var contacts = _contacts.asStateFlow()

    fun getContacts(userID: Int) {
        viewModelScope.launch {
            _contacts.value = api.getUserContacts(userID)
        }
    }

    fun addContact(id: Int, name: String, label: String) {
        viewModelScope.launch {
            try {
                val users: List<User> = api.getUsers()
                users.find { it.userName == name }?.let {
                    api.createContact(
                        Contact(
                            userID = id,
                            contactID = it.id!!,
                            label = label
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteContact(contactID: Int) {
        viewModelScope.launch {
            api.deleteContact(contactID)
        }
    }
}