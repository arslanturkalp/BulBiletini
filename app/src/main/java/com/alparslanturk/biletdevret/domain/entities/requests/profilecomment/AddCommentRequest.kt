package com.alparslanturk.biletdevret.domain.entities.requests.profilecomment

data class AddCommentRequest(
    val userId: String,
    val createdUserId: String,
    val comment: String
)
