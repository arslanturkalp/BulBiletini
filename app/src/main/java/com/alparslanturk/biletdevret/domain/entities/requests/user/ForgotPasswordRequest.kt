package com.alparslanturk.biletdevret.domain.entities.requests.user

data class ForgotPasswordRequest(
    val username: String,
    val email: String,
    val verificationCode: String,
    val newPassword: String
)
