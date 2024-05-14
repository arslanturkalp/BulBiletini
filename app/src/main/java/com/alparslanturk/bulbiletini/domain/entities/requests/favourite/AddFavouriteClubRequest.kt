package com.alparslanturk.bulbiletini.domain.entities.requests.favourite

data class AddFavouriteClubRequest(
    val userId: String,
    val clubId: String
)
