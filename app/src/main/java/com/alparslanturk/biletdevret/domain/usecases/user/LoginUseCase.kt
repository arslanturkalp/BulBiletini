package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.user.LoginRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<LoginRequest, LoginResponse>() {
    override suspend fun getData(params: LoginRequest?): Result<LoginResponse> = repository.login(params!!)
}