package com.alparslanturk.kombineapp.data.entities.models

data class Token(
    val accessToken: String,
    val expiration: String,
    val refreshToken: String
)
