package com.alparslanturk.kombineapp.domain.entities.responses.tariff

data class GetTariffListResponse(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val data: GetTariffListResponseItem
)
