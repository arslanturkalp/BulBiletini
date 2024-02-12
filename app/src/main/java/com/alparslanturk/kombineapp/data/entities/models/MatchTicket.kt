package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MatchTicket(
    val id: String,
    val matchId: String?,
    val price: Long,
    val location: String,
    val description: String,
    val userId: String
) : Parcelable