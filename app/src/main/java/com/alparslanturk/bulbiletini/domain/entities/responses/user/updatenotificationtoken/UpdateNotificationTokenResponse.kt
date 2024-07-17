package com.alparslanturk.bulbiletini.domain.entities.responses.user.updatenotificationtoken

data class UpdateNotificationTokenResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
