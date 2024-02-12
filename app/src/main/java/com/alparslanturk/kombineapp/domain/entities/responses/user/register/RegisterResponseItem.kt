package com.alparslanturk.kombineapp.domain.entities.responses.user.register

data class RegisterResponseItem(
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val dateOfBirth: String,
    val phoneNumber: String
)
