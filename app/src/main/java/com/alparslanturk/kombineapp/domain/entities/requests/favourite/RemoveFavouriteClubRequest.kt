package com.alparslanturk.kombineapp.domain.entities.requests.favourite

data class RemoveFavouriteClubRequest(
    val userId: String,
    val clubId: String
)
