package com.alparslanturk.bulbiletini.domain.entities.responses.usermessage

import com.alparslanturk.bulbiletini.data.entities.models.UserMessage

data class GetUserMessagesResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: UserMessage
)
