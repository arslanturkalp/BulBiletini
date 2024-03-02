package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import com.alparslanturk.kombineapp.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetTicketsResponse>() {
    override suspend fun getData(params: String?): Result<GetTicketsResponse> = repository.getTickets(params!!)
}