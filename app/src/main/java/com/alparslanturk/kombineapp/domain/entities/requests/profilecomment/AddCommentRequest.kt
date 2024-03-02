package com.alparslanturk.kombineapp.domain.entities.requests.profilecomment

data class AddCommentRequest(
    val userId: String,
    val comment: String
)
