package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.user.UserDeleteRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.deleteuser.UserDeleteResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UserDeleteUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<UserDeleteRequest, UserDeleteResponse>() {
    override suspend fun getData(params: UserDeleteRequest?): Result<UserDeleteResponse> = repository.deleteUser(params!!)
}