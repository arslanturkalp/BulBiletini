package com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetlistwithtickets

import com.alparslanturk.bulbiletini.data.entities.models.Club
import com.alparslanturk.bulbiletini.data.entities.models.Ticket

data class ClubGetListWithTicketsResponseItem(
    val clubList: List<Club>?,
    val ticketList: List<Ticket>?
)
