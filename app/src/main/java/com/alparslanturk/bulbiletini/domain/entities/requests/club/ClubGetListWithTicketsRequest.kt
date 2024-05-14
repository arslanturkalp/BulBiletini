package com.alparslanturk.bulbiletini.domain.entities.requests.club

data class ClubGetListWithTicketsRequest (
    val userID: String,
    val filterType: Int?
)