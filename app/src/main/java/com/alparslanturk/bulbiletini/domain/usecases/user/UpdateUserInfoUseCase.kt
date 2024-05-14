package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UpdateUserInfoRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.updateuser.UpdateUserInfoResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<UpdateUserInfoRequest, UpdateUserInfoResponse>() {
    override suspend fun getData(params: UpdateUserInfoRequest?): Result<UpdateUserInfoResponse> = repository.updateUserInfo(params!!)
}