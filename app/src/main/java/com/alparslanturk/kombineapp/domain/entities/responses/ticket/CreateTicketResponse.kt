package com.alparslanturk.kombineapp.domain.entities.responses.ticket

import com.alparslanturk.kombineapp.data.entities.models.MatchTicket

data class CreateTicketResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: MatchTicket
)
