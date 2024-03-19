package com.alparslanturk.biletdevret.domain.usecases.profilecomment

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.profilecomment.AddCommentRequest
import com.alparslanturk.biletdevret.domain.entities.responses.profilecomment.AddCommentResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<AddCommentRequest, AddCommentResponse>() {
    override suspend fun getData(params: AddCommentRequest?): Result<AddCommentResponse> = repository.addComment(params!!)
}