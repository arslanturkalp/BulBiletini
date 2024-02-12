package com.alparslanturk.kombineapp.domain.entities.responses.tariff

data class PurchaseTariffResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
)
