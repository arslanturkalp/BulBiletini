package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.usermessage.RetrieveMessagesResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RetrieveMessagesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<List<String>, RetrieveMessagesResponse>() {
    override suspend fun getData(params: List<String>?): Result<RetrieveMessagesResponse> = repository.retrieveMessages(params?.get(0)!!, params[1])
}