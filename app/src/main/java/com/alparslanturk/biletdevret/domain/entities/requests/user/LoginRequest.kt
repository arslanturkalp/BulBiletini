package com.alparslanturk.biletdevret.domain.entities.requests.user

data class LoginRequest(
    val username: String,
    val password: String
)