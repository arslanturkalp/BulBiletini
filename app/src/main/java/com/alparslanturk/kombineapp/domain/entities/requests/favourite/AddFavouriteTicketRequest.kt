package com.alparslanturk.kombineapp.domain.entities.requests.favourite

data class AddFavouriteTicketRequest(
    val userId: String,
    val ticketId: String
)
