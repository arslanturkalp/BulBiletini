package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetTariffListUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, GetTariffListResponse>() {
    override suspend fun getData(params: Any?): Result<GetTariffListResponse> = repository.getTariffList()
}