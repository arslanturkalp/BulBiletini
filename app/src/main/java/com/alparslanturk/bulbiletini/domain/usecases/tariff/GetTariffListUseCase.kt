package com.alparslanturk.bulbiletini.domain.usecases.tariff

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetTariffListUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, GetTariffListResponse>() {
    override suspend fun getData(params: Any?): Result<GetTariffListResponse> = repository.getTariffList()
}