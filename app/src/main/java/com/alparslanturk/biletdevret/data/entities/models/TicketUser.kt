package com.alparslanturk.biletdevret.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class TicketUser (
    val id: String,
    val name: String,
    val surname: String,
    val username: String,
    val email: String,
    val dateOfBirth: String,
    val phoneNumber: String?,
    val profilePhoto: String?,
    val identityNumber: String?,
    val canCall: Boolean,
    val rating: Double?,
    val isApproved: Boolean,
    val isShownPhoneNumber: Boolean,
    val isBlock: Boolean
) : Parcelable