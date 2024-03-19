package com.alparslanturk.biletdevret.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TicketMatch(
    val matchId: String,
    val homeTeamId: String,
    val homeName: String,
    val homeFirstColor: String,
    val homeSecondColor: String,
    val homeLogo: String,
    val homeStadium: String,
    val homeStadiumPlan: String,
    val awayName: String,
    val awayFirstColor: String,
    val awaySecondColor: String,
    val awayLogo: String,
    val awayStadium: String,
    val awayStadiumPlan: String,
    val matchDate: String,
    val time: String,
    val leagueID: String,
    val leagueName: String,
    val leagueImagePath: String
) : Parcelable