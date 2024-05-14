package com.alparslanturk.bulbiletini.domain.entities.responses.tariff

import com.alparslanturk.bulbiletini.data.entities.models.TariffCategoryList

data class GetTariffListResponseItem(
    val tariffCategoryList: List<TariffCategoryList>
)
