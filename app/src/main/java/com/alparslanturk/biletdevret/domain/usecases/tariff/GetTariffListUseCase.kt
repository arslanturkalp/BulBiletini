package com.alparslanturk.biletdevret.domain.usecases.tariff

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetTariffListUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, GetTariffListResponse>() {
    override suspend fun getData(params: Any?): Result<GetTariffListResponse> = repository.getTariffList()
}