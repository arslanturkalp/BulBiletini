package com.alparslanturk.biletdevret.domain.entities.responses.usermessage

import com.alparslanturk.biletdevret.data.entities.models.UserMessage

data class GetUserMessagesResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: UserMessage
)
