package com.alparslanturk.bulbiletini.domain.entities.responses.user.verificationcode

data class VerificationCodeResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: VerificationCodeResponseItem
)
