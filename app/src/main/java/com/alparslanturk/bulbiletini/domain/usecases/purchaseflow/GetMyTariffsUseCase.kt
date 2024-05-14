package com.alparslanturk.bulbiletini.domain.usecases.purchaseflow

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.tariff.GetMyTariffsResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMyTariffsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetMyTariffsResponse>() {
    override suspend fun getData(params: String?): Result<GetMyTariffsResponse> = repository.getMyTariffs(params!!)
}