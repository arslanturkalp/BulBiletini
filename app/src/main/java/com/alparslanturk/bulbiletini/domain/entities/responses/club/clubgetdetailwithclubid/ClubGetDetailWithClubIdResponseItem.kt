package com.alparslanturk.bulbiletini.domain.entities.responses.club.clubgetdetailwithclubid

import com.alparslanturk.bulbiletini.data.entities.models.Match
import com.alparslanturk.bulbiletini.data.entities.models.Ticket

data class ClubGetDetailWithClubIdResponseItem(
    val id: String,
    val name: String,
    val firstColor: String,
    val secondColor: String,
    val logo: String,
    val stadium: String,
    val stadiumPlan: String,
    val totalTicketCount: Int,
    val clubIsFavourite: Boolean,
    val matchList: List<Match>?,
    val ticketList: List<Ticket>?
)
