package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TicketUser (
    val userID: String,
    val name: String,
    val surname: String,
    val phoneNumber: String,
    val canCall: Boolean,
    val rating: String,
    val isApproved: Boolean
) : Parcelable