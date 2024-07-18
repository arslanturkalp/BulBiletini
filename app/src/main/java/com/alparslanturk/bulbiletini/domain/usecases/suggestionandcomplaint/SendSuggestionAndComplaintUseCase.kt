package com.alparslanturk.bulbiletini.domain.usecases.suggestionandcomplaint

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.suggestionandcomplaint.SendSuggestionAndComplaintRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.suggestionandcomplaint.SendSuggestionAndComplaintResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class SendSuggestionAndComplaintUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<SendSuggestionAndComplaintRequest, SendSuggestionAndComplaintResponse>() {
    override suspend fun getData(params: SendSuggestionAndComplaintRequest?): Result<SendSuggestionAndComplaintResponse> = repository.sendSuggestionAndComplaint(params?.userId.orEmpty(), params?.requestText.orEmpty())
}