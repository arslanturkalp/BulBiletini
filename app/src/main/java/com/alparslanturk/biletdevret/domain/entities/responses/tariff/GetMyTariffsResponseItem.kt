package com.alparslanturk.biletdevret.domain.entities.responses.tariff

import com.alparslanturk.biletdevret.data.entities.models.MyTariff

data class GetMyTariffsResponseItem(
    val purchaseFlowList: List<MyTariff>
)
