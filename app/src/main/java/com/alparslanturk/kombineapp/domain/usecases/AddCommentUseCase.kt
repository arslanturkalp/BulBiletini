package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.profilecomment.AddCommentRequest
import com.alparslanturk.kombineapp.domain.entities.responses.profilecomment.AddCommentResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddCommentRequest, AddCommentResponse>() {
    override suspend fun getData(params: AddCommentRequest?): Result<AddCommentResponse> = repository.addComment(params!!)
}