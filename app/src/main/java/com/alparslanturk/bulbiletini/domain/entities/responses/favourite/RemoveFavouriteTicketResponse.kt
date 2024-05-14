package com.alparslanturk.bulbiletini.domain.entities.responses.favourite

data class RemoveFavouriteTicketResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)