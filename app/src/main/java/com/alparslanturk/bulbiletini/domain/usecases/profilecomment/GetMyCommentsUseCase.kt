package com.alparslanturk.bulbiletini.domain.usecases.profilecomment

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment.GetMyCommentsResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetMyCommentsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetMyCommentsResponse>() {
    override suspend fun getData(params: String?): Result<GetMyCommentsResponse> = repository.getMyComments(params!!)
}