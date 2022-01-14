package com.example.contentprovider.data.models

data class Contact(
    val id: Long,
    val name: String,
    val phones: List<String>
)