package com.alparslanturk.bulbiletini.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tariff(
    val id: String,
    val name: String,
    val description: String,
    val price: String,
    val marketId: String?
) : Parcelable
