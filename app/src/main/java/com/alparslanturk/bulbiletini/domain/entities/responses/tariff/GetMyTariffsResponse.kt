package com.alparslanturk.bulbiletini.domain.entities.responses.tariff

data class GetMyTariffsResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: GetMyTariffsResponseItem
)
