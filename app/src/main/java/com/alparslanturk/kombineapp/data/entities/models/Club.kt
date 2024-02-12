package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Club (
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
): Parcelable