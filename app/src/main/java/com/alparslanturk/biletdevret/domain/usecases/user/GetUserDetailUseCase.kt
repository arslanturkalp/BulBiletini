package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.user.getuserdetail.GetUserDetailResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetUserDetailResponse>() {
    override suspend fun getData(params: String?): Result<GetUserDetailResponse> = repository.getUserDetail(params!!)
}