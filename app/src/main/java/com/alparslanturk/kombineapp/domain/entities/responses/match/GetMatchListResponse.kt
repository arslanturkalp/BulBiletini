package com.alparslanturk.kombineapp.domain.entities.responses.match

data class GetMatchListResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: GetMatchListResponseItem
)
