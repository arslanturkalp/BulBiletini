package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.user.RegisterRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.register.RegisterResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<RegisterRequest, RegisterResponse>() {
    override suspend fun getData(params: RegisterRequest?): Result<RegisterResponse> = repository.register(params!!)
}