package com.alparslanturk.bulbiletini.domain.entities.responses.user.forgotpassword

data class ForgotPasswordResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: ForgotPasswordResponseItem
)
