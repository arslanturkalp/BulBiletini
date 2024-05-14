package com.alparslanturk.bulbiletini.domain.usecases.profilecomment

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.profilecomment.AddCommentRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.profilecomment.AddCommentResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddCommentRequest, AddCommentResponse>() {
    override suspend fun getData(params: AddCommentRequest?): Result<AddCommentResponse> = repository.addComment(params!!)
}