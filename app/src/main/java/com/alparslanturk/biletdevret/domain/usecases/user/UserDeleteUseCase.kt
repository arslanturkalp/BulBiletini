package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UserDeleteUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<UserDeleteRequest, UserDeleteResponse>() {
    override suspend fun getData(params: UserDeleteRequest?): Result<UserDeleteResponse> = repository.deleteUser(params!!)
}