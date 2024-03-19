package com.alparslanturk.biletdevret.domain.usecases.usermessage

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetUserMessagesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetUserMessagesResponse>() {
    override suspend fun getData(params: String?): Result<GetUserMessagesResponse> = repository.getUserMessages(params!!)
}