package com.alparslanturk.kombineapp.domain.entities.requests.user

data class RegisterRequest(
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val email: String,
    val dateOfBirth: String,
    val phoneNumber: String,
    val canCall: Boolean,
    val isShownPhoneNumber: Boolean
)
