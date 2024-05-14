package com.alparslanturk.bulbiletini.domain.usecases.userrate

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.userrate.RateUserRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.userrate.RateUserResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RateUserUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RateUserRequest, RateUserResponse>() {
    override suspend fun getData(params: RateUserRequest?): Result<RateUserResponse> = repository.rateUser(params!!)
}