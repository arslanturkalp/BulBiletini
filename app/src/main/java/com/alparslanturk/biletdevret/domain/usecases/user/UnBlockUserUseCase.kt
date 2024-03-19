package com.alparslanturk.biletdevret.domain.usecases.user

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.userblacklist.BlockUserRequest
import com.alparslanturk.biletdevret.domain.entities.responses.user.blockuser.BlockUserResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UnBlockUserUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<BlockUserRequest, BlockUserResponse>() {
    override suspend fun getData(params: BlockUserRequest?): Result<BlockUserResponse> = repository.unBlockUser(params!!.blockedUserId, params.blockedByUserId)
}