package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RegisterRequest, RegisterResponse>() {
    override suspend fun getData(params: RegisterRequest?): Result<RegisterResponse> = repository.register(params!!)
}