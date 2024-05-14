package com.alparslanturk.bulbiletini.domain.usecases.user

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.userblacklist.BlockUserRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.user.blockuser.BlockUserResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class BlockUserUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<BlockUserRequest, BlockUserResponse>() {
    override suspend fun getData(params: BlockUserRequest?): Result<BlockUserResponse> = repository.blockUser(params!!.blockedUserId, params.blockedByUserId)
}