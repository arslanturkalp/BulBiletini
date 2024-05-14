package com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetlistwithtickets

data class ClubGetListWithTicketsResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: ClubGetListWithTicketsResponseItem
)
