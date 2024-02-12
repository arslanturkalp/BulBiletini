package com.alparslanturk.kombineapp.domain.usecases

import com.alparslanturk.kombineapp.data.entities.models.Result
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketRequest
import com.alparslanturk.kombineapp.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.kombineapp.domain.repository.Repository
import javax.inject.Inject

class CreateTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<CreateTicketRequest, CreateTicketResponse>() {
    override suspend fun getData(params: CreateTicketRequest?): Result<CreateTicketResponse> = repository.createTicket(params!!)
}