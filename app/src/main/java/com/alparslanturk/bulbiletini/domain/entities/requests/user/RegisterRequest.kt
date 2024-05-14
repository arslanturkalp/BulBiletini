package com.alparslanturk.bulbiletini.domain.entities.requests.user

data class RegisterRequest(
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val email: String
)
