package com.alparslanturk.bulbiletini.domain.usecases.purchaseflow

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.tariff.PurchaseTariffRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.tariff.PurchaseTariffResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class PurchaseTariffUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<PurchaseTariffRequest, PurchaseTariffResponse>() {
    override suspend fun getData(params: PurchaseTariffRequest?): Result<PurchaseTariffResponse> = repository.purchaseTariff(params!!)
}