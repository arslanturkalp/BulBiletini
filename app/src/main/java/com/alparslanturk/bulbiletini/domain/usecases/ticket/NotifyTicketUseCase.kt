package com.alparslanturk.bulbiletini.domain.usecases.ticket

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.requests.ticket.NotifyTicketRequest
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.NotifyTicketResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class NotifyTicketUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<NotifyTicketRequest, NotifyTicketResponse>() {
    override suspend fun getData(params: NotifyTicketRequest?): Result<NotifyTicketResponse> = repository.notifyTicket(params!!)
}