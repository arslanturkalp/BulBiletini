package com.alparslanturk.bulbiletini.domain.entities.requests.favourite

data class AddFavouriteTicketRequest(
    val userId: String,
    val ticketId: String
)
