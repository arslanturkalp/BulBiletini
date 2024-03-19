package com.alparslanturk.biletdevret.domain.entities.responses.profilecomment

data class AddCommentResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
