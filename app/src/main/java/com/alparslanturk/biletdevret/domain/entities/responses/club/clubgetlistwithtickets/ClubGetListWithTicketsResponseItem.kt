package com.alparslanturk.biletdevret.domain.entities.responses.club.clubgetlistwithtickets

import com.alparslanturk.biletdevret.data.entities.models.Club
import com.alparslanturk.biletdevret.data.entities.models.Ticket

data class ClubGetListWithTicketsResponseItem(
    val clubList: List<Club>?,
    val ticketList: List<Ticket>?
)
