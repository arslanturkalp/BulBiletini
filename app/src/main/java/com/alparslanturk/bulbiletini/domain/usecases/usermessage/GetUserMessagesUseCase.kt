package com.alparslanturk.bulbiletini.domain.usecases.usermessage

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.usermessage.GetUserMessagesResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetUserMessagesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetUserMessagesResponse>() {
    override suspend fun getData(params: String?): Result<GetUserMessagesResponse> = repository.getUserMessages(params!!)
}