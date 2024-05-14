package com.alparslanturk.bulbiletini.domain.entities.responses.userblacklist.getblockedusers

data class GetBlockedUsersResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: GetBlockedUsersResponseItem
)