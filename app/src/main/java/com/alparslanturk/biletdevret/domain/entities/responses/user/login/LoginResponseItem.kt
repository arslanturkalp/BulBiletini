package com.alparslanturk.biletdevret.domain.entities.responses.user.login

import com.alparslanturk.biletdevret.data.entities.models.Token

data class LoginResponseItem(
    val id: String,
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val token: Token
)
