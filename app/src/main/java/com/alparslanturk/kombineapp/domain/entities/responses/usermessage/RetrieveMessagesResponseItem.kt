package com.alparslanturk.kombineapp.domain.entities.responses.usermessage

import com.alparslanturk.kombineapp.data.entities.models.Message

data class RetrieveMessagesResponseItem(
    val userMessageList: List<Message>,
)
