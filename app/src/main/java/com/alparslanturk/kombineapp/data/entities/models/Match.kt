package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Match(
    val id: String,
    val homeTeamId: String,
    val homeTeamName: String,
    val homeTeamLogo: String,
    val awayTeamId: String,
    val awayTeamName: String?,
    val awayTeamLogo: String,
    val matchDate: String,
    val ticketCount: Int
): Parcelable
