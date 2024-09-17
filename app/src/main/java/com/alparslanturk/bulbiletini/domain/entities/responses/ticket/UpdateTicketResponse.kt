package com.alparslanturk.bulbiletini.domain.entities.responses.ticket

data class UpdateTicketResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)
