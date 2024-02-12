package com.alparslanturk.kombineapp.domain.entities.responses.ticket

data class GetTicketsResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: GetTicketsResponseItem
)
