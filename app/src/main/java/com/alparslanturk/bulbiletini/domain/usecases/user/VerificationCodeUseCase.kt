package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.user.verificationcode.VerificationCodeResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class VerificationCodeUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, VerificationCodeResponse>() {
    override suspend fun getData(params: String?): Result<VerificationCodeResponse> = repository.getVerificationCode(params!!)
}