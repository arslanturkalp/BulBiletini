package com.alparslanturk.bulbiletini.domain.entities.requests.profilecomment

data class AddCommentRequest(
    val userId: String,
    val createdUserId: String,
    val comment: String
)
