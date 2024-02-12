package com.alparslanturk.kombineapp.domain.entities.responses.ticket

import com.alparslanturk.kombineapp.data.entities.models.Ticket

data class GetTicketsResponseItem(
    val ticketList: List<Ticket>
)
