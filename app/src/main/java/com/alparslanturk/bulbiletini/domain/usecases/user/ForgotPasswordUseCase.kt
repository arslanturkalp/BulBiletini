package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ForgotPasswordRequest, ForgotPasswordResponse>() {
    override suspend fun getData(params: ForgotPasswordRequest?): Result<ForgotPasswordResponse> = repository.forgotPassword(params!!)
}