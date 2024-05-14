package com.alparslanturk.bulbiletini.domain.entities.responses.user.register

data class RegisterResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: RegisterResponseItem
)