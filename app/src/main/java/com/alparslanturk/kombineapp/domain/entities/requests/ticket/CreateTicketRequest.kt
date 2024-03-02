package com.alparslanturk.kombineapp.domain.entities.requests.ticket

data class CreateTicketRequest(
    val matchId: String,
    val price: Int,
    val tribune: String,
    val block: String,
    val order: String,
    val userId: String,
    val tariffCategoryId: String,
    val description: String
)
