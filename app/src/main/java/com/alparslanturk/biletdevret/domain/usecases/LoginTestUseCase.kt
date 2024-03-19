package com.alparslanturk.biletdevret.domain.usecases

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import okhttp3.ResponseBody
import javax.inject.Inject

class LoginTestUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, ResponseBody>() {
    override suspend fun getData(params: Any?): Result<ResponseBody> = repository.loginTest()
}