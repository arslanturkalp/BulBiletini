package com.alparslanturk.biletdevret.domain.usecases.profilecomment

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetCommentsResponse>() {
    override suspend fun getData(params: String?): Result<GetCommentsResponse> = repository.getComments(params!!)
}