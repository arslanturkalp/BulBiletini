package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val user: TicketUser,
    val homeTeamId: String,
    val homeTeamName: String,
    val homeTeamFirstColor: String,
    val homeTeamSecondColor: String,
    val homeTeamLogo: String,
    val homeTeamStadium: String,
    val homeTeamStadiumPlan: String,
    val awayTeamId: String,
    val awayTeamName: String,
    val awayTeamFirstColor: String,
    val awayTeamSecondColor: String,
    val awayTeamLogo: String,
    val awayTeamStadium: String,
    val awayTeamStadiumPlan: String,
    val matchId: String,
    val matchDate: String,
    val leagueId: String,
    val leagueName: String,
    val leagueImagePath: String,
    val ticketId: String,
    val price: Int,
    val location: String,
    val tribune: String,
    val block: String,
    val order: String,
    val ticketDescription: String,
    var ticketIsFavourite: Boolean
) : Parcelable
