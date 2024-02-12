package com.alparslanturk.kombineapp.domain.entities.responses.tariff

import com.alparslanturk.kombineapp.data.entities.models.TariffCategoryList

data class GetTariffListResponseItem(
    val tariffCategoryList: List<TariffCategoryList>
)
