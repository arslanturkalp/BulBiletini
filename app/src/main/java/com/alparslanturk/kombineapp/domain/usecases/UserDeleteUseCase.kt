package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.kombineapp.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UserDeleteUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<UserDeleteRequest, UserDeleteResponse>() {
    override suspend fun getData(params: UserDeleteRequest?): Result<UserDeleteResponse> = repository.deleteUser(params!!)
}