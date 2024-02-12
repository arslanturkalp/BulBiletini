package com.alparslanturk.kombineapp.domain.entities.requests.favourite

data class AddFavouriteClubRequest(
    val userId: String,
    val clubId: String
)
