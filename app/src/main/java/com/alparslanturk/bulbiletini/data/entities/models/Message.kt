package com.alparslanturk.bulbiletini.data.entities.models

import com.alparslanturk.bulbiletini.data.entities.enums.ChatMessageType

data class Message(
    val fromUserId: String,
    val toUserId: String,
    val message: String,
    val wasSeen: Boolean,
    val createdDate: String,
    val viewType: ChatMessageType,
    var monthAndYear: String
)
