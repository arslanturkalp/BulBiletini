package com.alparslanturk.kombineapp.data.entities.models

data class TariffCategoryList(
    val id: String,
    val name: String,
    val tariffList: ArrayList<Tariff>
)
