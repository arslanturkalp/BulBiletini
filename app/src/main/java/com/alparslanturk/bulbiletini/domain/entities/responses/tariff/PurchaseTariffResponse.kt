package com.alparslanturk.bulbiletini.domain.entities.responses.tariff

data class PurchaseTariffResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
)
