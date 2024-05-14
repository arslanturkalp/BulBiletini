package com.alparslanturk.bulbiletini.domain.usecases.usermessage

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.usermessage.SendMessageRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.usermessage.SendMessageResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<SendMessageRequest, SendMessageResponse>() {
    override suspend fun getData(params: SendMessageRequest?): Result<SendMessageResponse> = repository.sendMessage(params!!)
}