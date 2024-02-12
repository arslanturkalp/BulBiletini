package com.alparslanturk.kombineapp.domain.entities.responses.ticket

data class CreateTicketRequest(
    val matchId: String,
    val price: Int,
    val tribune: String,
    val block: String,
    val order: String,
    val userId: String,
    val tariffId: String,
    val description: String
)
