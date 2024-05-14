package com.alparslanturk.bulbiletini.domain.entities.requests.userblacklist

data class BlockUserRequest(
    val blockedUserId: String,
    val blockedByUserId: String
)
