package com.alparslanturk.bulbiletini.domain.entities.responses.user.blockuser

data class BlockUserResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
