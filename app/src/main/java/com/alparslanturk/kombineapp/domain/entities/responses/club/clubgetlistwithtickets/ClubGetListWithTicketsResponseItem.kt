package com.alparslanturk.kombineapp.domain.entities.responses.club.clubgetlistwithtickets

import com.alparslanturk.kombineapp.data.entities.models.Club
import com.alparslanturk.kombineapp.data.entities.models.Ticket

data class ClubGetListWithTicketsResponseItem(
    val clubList: List<Club>,
    val ticketList: List<Ticket>
)
