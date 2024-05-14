package com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment

data class GetCommentsResponse(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val data: GetCommentsResponseItem
)
