package com.alparslanturk.biletdevret.domain.entities.responses.ticket

import com.alparslanturk.biletdevret.data.entities.models.MatchTicket

data class CreateTicketResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: MatchTicket
)
