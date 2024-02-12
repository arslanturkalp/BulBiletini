package com.alparslanturk.kombineapp.domain.entities.requests.club

data class ClubGetListWithTicketsRequest (
    val userID: String,
    val filterType: Int?
)