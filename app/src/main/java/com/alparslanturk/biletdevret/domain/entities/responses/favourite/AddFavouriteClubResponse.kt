package com.alparslanturk.biletdevret.domain.entities.responses.favourite

data class AddFavouriteClubResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String
)