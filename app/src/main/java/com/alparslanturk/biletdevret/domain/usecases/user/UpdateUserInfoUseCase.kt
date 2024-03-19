package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.user.UpdateUserInfoRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.updateuser.UpdateUserInfoResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<UpdateUserInfoRequest, UpdateUserInfoResponse>() {
    override suspend fun getData(params: UpdateUserInfoRequest?): Result<UpdateUserInfoResponse> = repository.updateUserInfo(params!!)
}