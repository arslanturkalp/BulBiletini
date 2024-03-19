package com.alparslanturk.biletdevret.domain.entities.responses.user.login

data class LoginResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: LoginResponseItem
)