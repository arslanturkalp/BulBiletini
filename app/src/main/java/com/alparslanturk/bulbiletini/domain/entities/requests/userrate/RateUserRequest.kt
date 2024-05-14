package com.alparslanturk.bulbiletini.domain.entities.requests.userrate

data class RateUserRequest(
    val userId: String,
    val createdUserId: String,
    val rate: Double
)
