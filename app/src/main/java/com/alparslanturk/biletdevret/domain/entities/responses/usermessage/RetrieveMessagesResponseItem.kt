package com.alparslanturk.biletdevret.domain.entities.responses.usermessage

import com.alparslanturk.biletdevret.data.entities.models.Message

data class RetrieveMessagesResponseItem(
    val userMessageList: List<Message>,
)
