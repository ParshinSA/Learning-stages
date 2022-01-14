package com.example.contentprovider.ui.fragment.contactlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.data.models.Contact
import com.example.contentprovider.data.repositories.ContactRepository
import com.example.contentprovider.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ContactListViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository = ContactRepository(application)

    private val callMutableLiveData = SingleLiveEvent<String>()
    val callLiveData: LiveData<String>
        get() = callMutableLiveData

    private val contactsMutableLiveData = MutableLiveData<List<Contact>>()
    val contactsLiveData: LiveData<List<Contact>>
        get() = contactsMutableLiveData


    fun loadList() {
        viewModelScope.launch {
            try {
                contactsMutableLiveData.postValue(contactRepository.getAllContacts())
            } catch (t: Throwable) {
                Log.e("ContactListViewModel", "contact list error", t)
                contactsMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun callToContact(contact: Contact) {
        contact.phones.firstOrNull()?.let { callMutableLiveData.postValue(it) }
    }
}