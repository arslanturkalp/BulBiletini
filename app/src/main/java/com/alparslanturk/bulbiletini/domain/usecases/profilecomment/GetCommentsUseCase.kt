package com.alparslanturk.bulbiletini.domain.usecases.profilecomment

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetCommentsResponse>() {
    override suspend fun getData(params: String?): Result<GetCommentsResponse> = repository.getComments(params!!)
}