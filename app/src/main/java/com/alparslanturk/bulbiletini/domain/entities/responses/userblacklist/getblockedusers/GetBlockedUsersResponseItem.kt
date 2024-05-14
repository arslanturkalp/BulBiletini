package com.alparslanturk.bulbiletini.domain.entities.responses.userblacklist.getblockedusers

import com.alparslanturk.bulbiletini.data.entities.models.TicketUser

data class GetBlockedUsersResponseItem(
    val blockedUserList: List<TicketUser>
)
