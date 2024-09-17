package com.alparslanturk.bulbiletini.domain.entities.requests.ticket

data class UpdateTicketRequest(
    val ticketId: String,
    val newPrice: Int,
    val newDescription: String
)