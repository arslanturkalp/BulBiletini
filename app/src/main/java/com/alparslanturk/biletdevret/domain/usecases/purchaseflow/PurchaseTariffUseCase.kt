package com.alparslanturk.biletdevret.domain.usecases.purchaseflow

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.biletdevret.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class PurchaseTariffUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<PurchaseTariffRequest, PurchaseTariffResponse>() {
    override suspend fun getData(params: PurchaseTariffRequest?): Result<PurchaseTariffResponse> = repository.purchaseTariff(params!!)
}