package com.alparslanturk.bulbiletini.domain.entities.responses.user.verificationcode

data class VerificationCodeResponseItem(
    val email: String,
    val username: String,
    val verificationCode: String,
    val verificationCodeDate: String,
    val isMailSent: Boolean
)
