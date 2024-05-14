package com.alparslanturk.bulbiletini.domain.entities.responses.user.login

import com.alparslanturk.bulbiletini.data.entities.models.Token

data class LoginResponseItem(
    val id: String,
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val token: Token
)
