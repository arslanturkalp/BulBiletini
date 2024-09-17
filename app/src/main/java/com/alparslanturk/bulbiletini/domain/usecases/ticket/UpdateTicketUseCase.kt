package com.alparslanturk.bulbiletini.domain.usecases.ticket

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.NotifyTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.UpdateTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.NotifyTicketResponse
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.UpdateTicketResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<UpdateTicketRequest, UpdateTicketResponse>() {
    override suspend fun getData(params: UpdateTicketRequest?): Result<UpdateTicketResponse> = repository.updateTicket(params!!)
}