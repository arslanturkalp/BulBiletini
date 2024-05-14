package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.LoginRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.login.LoginResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<LoginRequest, LoginResponse>() {
    override suspend fun getData(params: LoginRequest?): Result<LoginResponse> = repository.login(params!!)
}