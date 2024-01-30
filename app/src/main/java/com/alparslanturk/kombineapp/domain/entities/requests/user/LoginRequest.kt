package com.alparslanturk.kombineapp.domain.entities.requests.user

data class LoginRequest(
    val username: String,
    val password: String
)