package com.alparslanturk.bulbiletini.domain.entities.responses.match

data class GetMatchListResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: GetMatchListResponseItem
)
