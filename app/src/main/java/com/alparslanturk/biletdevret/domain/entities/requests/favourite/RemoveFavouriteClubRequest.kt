package com.alparslanturk.biletdevret.domain.entities.requests.favourite

data class RemoveFavouriteClubRequest(
    val userId: String,
    val clubId: String
)
