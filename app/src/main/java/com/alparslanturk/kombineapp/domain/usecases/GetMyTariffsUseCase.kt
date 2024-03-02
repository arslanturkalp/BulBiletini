package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.tariff.GetMyTariffsResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMyTariffsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetMyTariffsResponse>() {
    override suspend fun getData(params: String?): Result<GetMyTariffsResponse> = repository.getMyTariffs(params!!)
}