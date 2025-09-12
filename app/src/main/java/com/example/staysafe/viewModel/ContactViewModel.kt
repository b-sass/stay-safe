package com.example.staysafe.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staysafe.data.models.Contact
import com.example.staysafe.data.models.UserContact
import com.example.staysafe.data.repositories.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object ContactViewModel: ViewModel() {

    val api = ApiRepository()
    private val _contacts = MutableStateFlow<List<UserContact>>(emptyList())
    var contacts = _contacts.asStateFlow();

    var userID: Int? = null

    fun getUserContacts() {
        viewModelScope.launch {
            _contacts.value = api.getUserContacts(userID!!)
        }
    }

    fun createContact(contact: String) {
        viewModelScope.launch {
            api.createContact(contact)
            getUserContacts()
        }
    }

    fun deleteContact(contactID: Int) {
        viewModelScope.launch {
            api.deleteContact(userID!!, contactID)
            getUserContacts()
        }
    }
}