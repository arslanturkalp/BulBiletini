package com.alparslanturk.biletdevret.domain.entities.responses.tariff

import com.alparslanturk.biletdevret.data.entities.models.TariffCategoryList

data class GetTariffListResponseItem(
    val tariffCategoryList: List<TariffCategoryList>
)
