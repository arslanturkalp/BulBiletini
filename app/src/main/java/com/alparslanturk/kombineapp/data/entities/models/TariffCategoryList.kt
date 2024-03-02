package com.alparslanturk.kombineapp.data.entities.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TariffCategoryList(
    val id: String,
    val name: String,
    val tariffList: ArrayList<Tariff>
) : Parcelable
