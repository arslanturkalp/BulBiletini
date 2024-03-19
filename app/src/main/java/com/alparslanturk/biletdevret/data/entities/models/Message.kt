package com.alparslanturk.biletdevret.data.entities.models

import com.alparslanturk.biletdevret.data.entities.enums.ChatMessageType

data class Message(
    val fromUserId: String,
    val toUserId: String,
    val message: String,
    val wasSeen: Boolean,
    val createdDate: String,
    val viewType: ChatMessageType,
    var monthAndYear: String
)
