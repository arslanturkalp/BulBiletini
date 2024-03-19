package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.userblacklist.getblockedusers.GetBlockedUsersResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetBlockedUsersUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetBlockedUsersResponse>() {
    override suspend fun getData(params: String?): Result<GetBlockedUsersResponse> = repository.getBlockedUsers(params!!)
}