package com.alparslanturk.kombineapp.domain.entities.responses.tariff

import com.alparslanturk.kombineapp.data.entities.models.MyTariff

data class GetMyTariffsResponseItem(
    val purchaseFlowList: List<MyTariff>
)
