package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetTariffListResponse
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import javax.inject.Inject

class PurchaseTariffUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<PurchaseTariffRequest, PurchaseTariffResponse>() {
    override suspend fun getData(params: PurchaseTariffRequest?): Result<PurchaseTariffResponse> = repository.purchaseTariff(params!!)
}