package com.alparslanturk.biletdevret.domain.entities.requests.usermessage

data class SendMessageRequest(
    val fromUserId: String,
    val toUserId: String,
    val message: String
)
