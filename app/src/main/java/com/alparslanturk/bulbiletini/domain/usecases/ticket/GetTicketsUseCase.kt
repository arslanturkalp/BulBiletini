package com.alparslanturk.bulbiletini.domain.usecases.ticket

import com.alparslanturk.bulbiletini.data.entities.models.Result
import com.alparslanturk.bulbiletini.domain.entities.responses.ticket.GetTicketsResponse
import com.alparslanturk.bulbiletini.domain.repository.Repository
import com.alparslanturk.bulbiletini.domain.usecases.base.BaseUseCase
import javax.inject.Inject

class GetTicketsUseCase @Inject constructor(private val repository: Repository) : BaseUseCase<String, GetTicketsResponse>() {
    override suspend fun getData(params: String?): Result<GetTicketsResponse> = repository.getTickets(params!!)
}