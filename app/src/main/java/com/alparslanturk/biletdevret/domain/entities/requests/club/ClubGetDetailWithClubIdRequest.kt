package com.alparslanturk.biletdevret.domain.entities.requests.club

data class ClubGetDetailWithClubIdRequest(
    val clubID: String,
    val userID: String
)