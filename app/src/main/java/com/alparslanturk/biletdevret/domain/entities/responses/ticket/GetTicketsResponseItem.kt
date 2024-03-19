package com.alparslanturk.biletdevret.domain.entities.responses.ticket

import com.alparslanturk.biletdevret.data.entities.models.Ticket

data class GetTicketsResponseItem(
    val ticketList: List<Ticket>
)
