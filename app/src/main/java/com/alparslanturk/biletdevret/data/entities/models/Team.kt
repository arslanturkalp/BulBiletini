package com.alparslanturk.biletdevret.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    val id: String,
    val name: String,
    val firstColor: String,
    val secondColor: String,
    val logo: String,
    val totalTicket: String
) : Parcelable
