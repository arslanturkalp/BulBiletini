package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.profilecomment.GetCommentsResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetCommentsResponse>() {
    override suspend fun getData(params: String?): Result<GetCommentsResponse> = repository.getComments(params!!)
}