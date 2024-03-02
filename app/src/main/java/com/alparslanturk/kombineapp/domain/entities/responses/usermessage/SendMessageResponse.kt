package com.alparslanturk.kombineapp.domain.entities.responses.usermessage

data class SendMessageResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
