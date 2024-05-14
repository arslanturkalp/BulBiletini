package com.alparslanturk.bulbiletini.domain.entities.responses.usermessage

data class RetrieveMessagesResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: RetrieveMessagesResponseItem
)
