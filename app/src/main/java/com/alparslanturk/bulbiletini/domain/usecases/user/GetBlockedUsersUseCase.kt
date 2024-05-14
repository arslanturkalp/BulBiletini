package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.userblacklist.getblockedusers.GetBlockedUsersResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetBlockedUsersUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetBlockedUsersResponse>() {
    override suspend fun getData(params: String?): Result<GetBlockedUsersResponse> = repository.getBlockedUsers(params!!)
}