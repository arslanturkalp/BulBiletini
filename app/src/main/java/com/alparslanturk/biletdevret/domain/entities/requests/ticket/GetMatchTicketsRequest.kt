package com.alparslanturk.biletdevret.domain.entities.requests.ticket

data class GetMatchTicketsRequest(
    val matchID: String,
    val userID: String
)
