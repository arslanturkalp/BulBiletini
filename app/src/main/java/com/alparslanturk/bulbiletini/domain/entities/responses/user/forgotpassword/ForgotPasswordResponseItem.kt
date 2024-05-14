package com.alparslanturk.bulbiletini.domain.entities.responses.user.forgotpassword

data class ForgotPasswordResponseItem(
    val id: String,
    val name: String,
    val surname: String,
    val username: String,
    val email: String
)
