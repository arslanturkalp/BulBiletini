package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.kombineapp.domain.entities.responses.usermessage.SendMessageResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<SendMessageRequest, SendMessageResponse>() {
    override suspend fun getData(params: SendMessageRequest?): Result<SendMessageResponse> = repository.sendMessage(params!!)
}