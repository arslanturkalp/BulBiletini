package com.alparslanturk.kombineapp.domain.entities.responses.item

data class Token(
    val accessToken: String,
    val expiration: String,
    val refreshToken: String
)
