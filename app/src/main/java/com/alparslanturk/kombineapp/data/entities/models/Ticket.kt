package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ticket(
    val id: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeLogo: String,
    val awayLogo: String,
    val matchDate: String,
    val price: String,
    val isFavourite: Boolean,
    val location: String,
    val stadium: String,
    val weather: String,
    val comment: String,
    val league: String,
    val user: TicketUser
) : Parcelable
