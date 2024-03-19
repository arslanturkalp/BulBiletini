package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ForgotPasswordRequest, ForgotPasswordResponse>() {
    override suspend fun getData(params: ForgotPasswordRequest?): Result<ForgotPasswordResponse> = repository.forgotPassword(params!!)
}