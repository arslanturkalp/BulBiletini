package com.alparslanturk.bulbiletini.domain.usecases

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import okhttp3.ResponseBody
import javax.inject.Inject

class LoginTestUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<Any, ResponseBody>() {
    override suspend fun getData(params: Any?): Result<ResponseBody> = repository.loginTest()
}