package com.example.contentprovider.data.repositories

import android.content.Context
import com.example.contentprovider.data.exception.IncorrectFormException
import com.example.contentprovider.data.models.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class ContactRepository(
    context: Context
) {
    private val phonePattern = Pattern.compile("^\\+?[0-9]{3}-?[0-9]{6,12}\$")


    suspend fun saveContact(name: String, phone: String) = withContext(Dispatchers.IO) {
        if (phonePattern.matcher(phone).matches().not() || name.isBlank()) {
            throw IncorrectFormException()
        }

    }

    suspend fun getAllContacts(): List<Contact> {
        return withContext(Dispatchers.IO) {
            
        }
    }
}