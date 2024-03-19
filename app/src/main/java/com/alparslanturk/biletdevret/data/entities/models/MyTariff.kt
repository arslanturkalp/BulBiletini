package com.alparslanturk.biletdevret.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyTariff(
    val tariffCategoryId: String,
    val tariffCategoryName: String,
    val quantity: Int
) : Parcelable
