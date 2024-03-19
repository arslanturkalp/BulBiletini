package com.alparslanturk.biletdevret.domain.entities.requests.favourite

data class AddFavouriteClubRequest(
    val userId: String,
    val clubId: String
)
