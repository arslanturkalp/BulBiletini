package com.alparslanturk.bulbiletini.domain.entities.responses.usermessage

import com.alparslanturk.bulbiletini.data.entities.models.Message

data class RetrieveMessagesResponseItem(
    val userMessageList: List<Message>,
)
