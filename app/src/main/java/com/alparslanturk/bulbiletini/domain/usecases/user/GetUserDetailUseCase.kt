package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.user.getuserdetail.GetUserDetailResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetUserDetailResponse>() {
    override suspend fun getData(params: String?): Result<GetUserDetailResponse> = repository.getUserDetail(params!!)
}