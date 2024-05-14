package com.alparslanturk.bulbiletini.domain.entities.responses.favourite

data class AddFavouriteTicketResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)