package com.alparslanturk.bulbiletini.domain.entities.responses.ticket

import com.alparslanturk.bulbiletini.data.entities.models.MatchTicket

data class CreateTicketResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: MatchTicket
)
