package com.alparslanturk.bulbiletini.domain.entities.responses.user.deleteuser

data class UserDeleteResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
