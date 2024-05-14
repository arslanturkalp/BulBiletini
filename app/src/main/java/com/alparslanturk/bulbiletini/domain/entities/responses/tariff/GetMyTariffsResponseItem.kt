package com.alparslanturk.bulbiletini.domain.entities.responses.tariff

import com.alparslanturk.bulbiletini.data.entities.models.MyTariff

data class GetMyTariffsResponseItem(
    val purchaseFlowList: List<MyTariff>
)
