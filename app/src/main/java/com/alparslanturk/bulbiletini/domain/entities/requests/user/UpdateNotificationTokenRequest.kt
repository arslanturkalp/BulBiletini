package com.alparslanturk.bulbiletini.domain.entities.requests.user

data class UpdateNotificationTokenRequest(
    val userId: String,
    val notificationToken: String
)
