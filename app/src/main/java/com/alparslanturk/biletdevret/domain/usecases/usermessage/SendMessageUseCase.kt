package com.alparslanturk.biletdevret.domain.usecases.usermessage

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.biletdevret.domain.entities.responses.usermessage.SendMessageResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<SendMessageRequest, SendMessageResponse>() {
    override suspend fun getData(params: SendMessageRequest?): Result<SendMessageResponse> = repository.sendMessage(params!!)
}