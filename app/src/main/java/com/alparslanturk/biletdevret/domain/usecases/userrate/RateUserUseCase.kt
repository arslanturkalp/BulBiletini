package com.alparslanturk.biletdevret.domain.usecases.userrate

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.userrate.RateUserRequest
import com.alparslanturk.biletdevret.domain.entities.responses.userrate.RateUserResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RateUserUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RateUserRequest, RateUserResponse>() {
    override suspend fun getData(params: RateUserRequest?): Result<RateUserResponse> = repository.rateUser(params!!)
}