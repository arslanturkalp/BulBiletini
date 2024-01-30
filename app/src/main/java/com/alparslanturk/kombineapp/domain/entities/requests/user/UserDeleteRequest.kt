package com.alparslanturk.kombineapp.domain.entities.requests.user

data class UserDeleteRequest(
    val username: String,
    val password: String,
    val email: String
)
