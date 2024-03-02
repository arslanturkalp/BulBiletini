package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetUserMessagesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetUserMessagesResponse>() {
    override suspend fun getData(params: String?): Result<GetUserMessagesResponse> = repository.getUserMessages(params!!)
}