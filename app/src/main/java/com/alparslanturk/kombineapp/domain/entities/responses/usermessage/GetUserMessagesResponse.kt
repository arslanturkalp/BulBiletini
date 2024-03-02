package com.alparslanturk.kombineapp.domain.entities.responses.usermessage

import com.alparslanturk.kombineapp.data.entities.models.UserMessage

data class GetUserMessagesResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: UserMessage?
)
