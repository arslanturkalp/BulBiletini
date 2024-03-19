package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.user.verificationcode.VerificationCodeResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class VerificationCodeUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, VerificationCodeResponse>() {
    override suspend fun getData(params: String?): Result<VerificationCodeResponse> = repository.getVerificationCode(params!!)
}