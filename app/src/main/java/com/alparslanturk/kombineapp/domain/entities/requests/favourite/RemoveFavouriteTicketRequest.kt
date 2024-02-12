package com.alparslanturk.kombineapp.domain.entities.requests.favourite

data class RemoveFavouriteTicketRequest(
    val userId: String,
    val ticketId: String
)
