package com.alparslanturk.biletdevret.domain.entities.responses.user.blockuser

data class BlockUserResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
