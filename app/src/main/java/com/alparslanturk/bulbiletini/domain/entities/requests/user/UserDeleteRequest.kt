package com.alparslanturk.bulbiletini.domain.entities.requests.user

data class UserDeleteRequest(
    val username: String,
    val password: String,
    val email: String
)
