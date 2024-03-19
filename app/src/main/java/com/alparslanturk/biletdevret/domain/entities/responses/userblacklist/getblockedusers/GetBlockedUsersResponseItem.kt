package com.alparslanturk.biletdevret.domain.entities.responses.userblacklist.getblockedusers

import com.alparslanturk.biletdevret.data.entities.models.TicketUser

data class GetBlockedUsersResponseItem(
    val blockedUserList: List<TicketUser>
)
