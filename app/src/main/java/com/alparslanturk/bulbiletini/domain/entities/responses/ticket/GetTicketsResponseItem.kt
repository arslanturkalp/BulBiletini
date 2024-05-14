package com.alparslanturk.bulbiletini.domain.entities.responses.ticket

import com.alparslanturk.bulbiletini.data.entities.models.Ticket

data class GetTicketsResponseItem(
    val ticketList: List<Ticket>
)
