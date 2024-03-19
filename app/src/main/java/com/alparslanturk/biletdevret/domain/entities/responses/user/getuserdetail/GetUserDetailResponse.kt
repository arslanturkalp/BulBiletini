package com.alparslanturk.biletdevret.domain.entities.responses.user.getuserdetail

import com.alparslanturk.biletdevret.data.entities.models.TicketUser

data class GetUserDetailResponse(
    val data: TicketUser,
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
