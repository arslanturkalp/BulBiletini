package com.alparslanturk.biletdevret.domain.usecases.usermessage

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.usermessage.RetrieveMessagesResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class RetrieveMessagesUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<List<String>, RetrieveMessagesResponse>() {
    override suspend fun getData(params: List<String>?): Result<RetrieveMessagesResponse> = repository.retrieveMessages(params?.get(0)!!, params[1])
}