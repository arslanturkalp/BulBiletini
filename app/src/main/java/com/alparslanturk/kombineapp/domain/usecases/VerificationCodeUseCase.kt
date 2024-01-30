package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.user.VerificationCodeRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.verificationcode.VerificationCodeResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import javax.inject.Inject

class VerificationCodeUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, VerificationCodeResponse>() {
    override suspend fun getData(params: String?): Result<VerificationCodeResponse> = repository.getVerificationCode(params!!)
}