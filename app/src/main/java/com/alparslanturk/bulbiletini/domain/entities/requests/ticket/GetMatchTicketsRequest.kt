package com.alparslanturk.bulbiletini.domain.entities.requests.ticket

data class GetMatchTicketsRequest(
    val matchID: String,
    val userID: String
)
