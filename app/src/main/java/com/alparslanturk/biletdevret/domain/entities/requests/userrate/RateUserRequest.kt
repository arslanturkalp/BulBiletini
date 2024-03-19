package com.alparslanturk.biletdevret.domain.entities.requests.userrate

data class RateUserRequest(
    val userId: String,
    val createdUserId: String,
    val rate: Double
)
