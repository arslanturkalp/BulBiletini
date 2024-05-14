package com.alparslanturk.bulbiletini.domain.entities.requests.usermessage

data class SendMessageRequest(
    val fromUserId: String,
    val toUserId: String,
    val message: String
)
