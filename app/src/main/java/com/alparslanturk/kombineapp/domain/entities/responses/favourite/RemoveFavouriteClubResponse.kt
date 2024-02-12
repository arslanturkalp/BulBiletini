package com.alparslanturk.kombineapp.domain.entities.responses.favourite

data class RemoveFavouriteClubResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)