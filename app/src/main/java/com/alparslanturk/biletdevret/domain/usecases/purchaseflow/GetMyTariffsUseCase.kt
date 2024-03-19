package com.alparslanturk.biletdevret.domain.usecases.purchaseflow

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.tariff.GetMyTariffsResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMyTariffsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetMyTariffsResponse>() {
    override suspend fun getData(params: String?): Result<GetMyTariffsResponse> = repository.getMyTariffs(params!!)
}