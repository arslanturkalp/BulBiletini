package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TicketUser (
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val dateOfBirth: String,
    val phoneNumber: String?,
    val canCall: Boolean,
    val rating: Long,
    val isApproved: Boolean,
    val isShownPhoneNumber: Boolean
) : Parcelable