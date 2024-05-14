package com.alparslanturk.bulbiletini.domain.entities.requests.ticket

data class NotifyTicketRequest(
    val userId: String,
    val ticketId: String,
    val message: String
)