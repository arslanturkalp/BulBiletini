package com.alparslanturk.bulbiletini.domain.usecases.ticket

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.CreateTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.CreateTicketResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class CreateTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<CreateTicketRequest, CreateTicketResponse>() {
    override suspend fun getData(params: CreateTicketRequest?): Result<CreateTicketResponse> = repository.createTicket(params!!)
}