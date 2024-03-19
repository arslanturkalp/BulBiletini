package com.alparslanturk.biletdevret.domain.entities.requests.favourite

data class RemoveFavouriteTicketRequest(
    val userId: String,
    val ticketId: String
)
