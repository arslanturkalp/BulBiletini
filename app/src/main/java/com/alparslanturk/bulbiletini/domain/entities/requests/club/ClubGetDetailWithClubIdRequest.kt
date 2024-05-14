package com.alparslanturk.bulbiletini.domain.entities.requests.club

data class ClubGetDetailWithClubIdRequest(
    val clubID: String,
    val userID: String
)