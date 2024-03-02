package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.user.ForgotPasswordRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.forgotpassword.ForgotPasswordResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<ForgotPasswordRequest, ForgotPasswordResponse>() {
    override suspend fun getData(params: ForgotPasswordRequest?): Result<ForgotPasswordResponse> = repository.forgotPassword(params!!)
}