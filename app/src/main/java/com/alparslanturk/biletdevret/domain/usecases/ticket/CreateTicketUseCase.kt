package com.alparslanturk.biletdevret.domain.usecases.ticket

import com.alparslanturk.biletdevret.data.entities.models.Result
import com.alparslanturk.biletdevret.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.biletdevret.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.biletdevret.domain.repository.Repository
import com.alparslanturk.biletdevret.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class CreateTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<CreateTicketRequest, CreateTicketResponse>() {
    override suspend fun getData(params: CreateTicketRequest?): Result<CreateTicketResponse> = repository.createTicket(params!!)
}