package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RegisterRequest, RegisterResponse>() {
    override suspend fun getData(params: RegisterRequest?): Result<RegisterResponse> = repository.register(params!!)
}