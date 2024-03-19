package com.alparslanturk.biletdevret.domain.entities.requests.userblacklist

data class BlockUserRequest(
    val blockedUserId: String,
    val blockedByUserId: String
)
