package com.alparslanturk.biletdevret.domain.usecases.ticket

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetTicketsResponse>() {
    override suspend fun getData(params: String?): Result<GetTicketsResponse> = repository.getTickets(params!!)
}