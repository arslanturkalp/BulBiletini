package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.user.LoginRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<LoginRequest, LoginResponse>() {
    override suspend fun getData(params: LoginRequest?): Result<LoginResponse> = repository.login(params!!)
}