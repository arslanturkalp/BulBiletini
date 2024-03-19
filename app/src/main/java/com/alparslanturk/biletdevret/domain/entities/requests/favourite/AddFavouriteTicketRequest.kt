package com.alparslanturk.biletdevret.domain.entities.requests.favourite

data class AddFavouriteTicketRequest(
    val userId: String,
    val ticketId: String
)
